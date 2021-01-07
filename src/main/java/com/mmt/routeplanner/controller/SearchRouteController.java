package com.mmt.routeplanner.controller;

import com.mmt.routeplanner.graph.GraphT;
import com.mmt.routeplanner.graph.Medium;
import com.mmt.routeplanner.model.SearchRequest;
import com.mmt.routeplanner.model.SearchResult;
import com.mmt.routeplanner.service.SearchRoute;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.jgrapht.GraphPath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/${base.version}/finder")
@CrossOrigin
public class SearchRouteController {

    @Autowired
    private SearchRoute searchRoute;

    @PostMapping("/")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong")})
    public SearchResult search(@Valid @RequestBody SearchRequest searchRequest, BindingResult result,
                                     HttpServletRequest httpServletRequest) {
        List<GraphPath<String, Medium>> graphs = GraphT.defaultGetPath(searchRequest.getSource(),searchRequest.getDestination());
        return searchRoute.searchRoutes(graphs,searchRequest.getSearchDate(),searchRequest.getSource(),searchRequest.getDestination(),searchRequest.getRouteSort());
    }

}
