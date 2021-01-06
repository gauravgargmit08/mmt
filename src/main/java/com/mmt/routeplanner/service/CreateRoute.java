package com.mmt.routeplanner.service;

import com.mmt.routeplanner.graph.Medium;
import com.mmt.routeplanner.model.Route;
import java.util.Collections;
import java.util.List;
import org.jgrapht.GraphPath;
import org.jgrapht.graph.GraphWalk;
import org.springframework.stereotype.Service;

@Service
public class CreateRoute {

  public List<Route> allRoutes( List<GraphPath<String, Medium>> graphs){

    graphs.forEach(stringMediumGraphPath -> {
      List<Medium> mediumList = ((GraphWalk) stringMediumGraphPath.getEdgeList();


    });


    return Collections.emptyList();

  }

}
