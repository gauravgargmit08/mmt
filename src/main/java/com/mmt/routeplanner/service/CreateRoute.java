package com.mmt.routeplanner.service;

import com.mmt.routeplanner.entity.BaseMedium;
import com.mmt.routeplanner.factory.MediumServiceFactory;
import com.mmt.routeplanner.graph.Medium;
import com.mmt.routeplanner.model.Route;
import com.mmt.routeplanner.model.SearchResult;
import com.mmt.routeplanner.util.RouteUtil;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.jgrapht.GraphPath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateRoute {

  @Autowired
  private FlightService flightService;

  @Autowired
  private MediumServiceFactory mediumServiceFactory;

  public SearchResult searchRoutesCheapest(List<GraphPath<String, Medium>> graphs, Date startDate,
      String src, String dest) {
    SearchResult searchResult = SearchResult.builder().destination(dest).source(src)
        .searchDate(startDate).build();
    List<Route> listRoutes = new ArrayList<>();
    BigDecimal totalFare = BigDecimal.ZERO;
    for (GraphPath<String, Medium> stringMediumGraphPath : graphs) {
      List<Medium> graphWalk = stringMediumGraphPath.getEdgeList();
      System.out.println(graphWalk.toString());
      Date nextDate = RouteUtil.atStartOfDay(startDate);
      Date endDate = RouteUtil.atEndOfDay(startDate);
      boolean routeFound = Boolean.TRUE;
      for (Medium medium : graphWalk) {
        String[] strArray = medium.getType().split(RouteUtil.DELIMETER);
        IMediumService mediumService =
            mediumServiceFactory.factory(strArray[2]);
        Optional<? extends BaseMedium> optionalBaseMedium = mediumService.
            findCheapestFlightsBySrcAndByDestAndByDate(strArray[0], strArray[1], nextDate, endDate);
        if (optionalBaseMedium.isPresent()) {
          BaseMedium baseMedium = optionalBaseMedium.get();
          nextDate = baseMedium.getEndDate();
          endDate = RouteUtil.addHours(nextDate, 24L);
          listRoutes.add(buildRoute(baseMedium));
          totalFare = totalFare.add(baseMedium.getFare());
        } else {
          routeFound = Boolean.FALSE;
        }

      }
      if (routeFound) {
        searchResult.setRoutes(listRoutes);
      } else {
        searchResult.setRoutes(Collections.emptyList());
        searchResult.setTotalFare(BigDecimal.ZERO);
      }
      searchResult.setTotalFare(totalFare);
    }
    return searchResult;

  }

  private Route buildRoute(BaseMedium baseMedium) {

    return Route.builder().destination(baseMedium.getDestination()).endDate(baseMedium.getEndDate())
        .
            fare(baseMedium.getFare()).startDate(baseMedium.getStartDate())
        .source(baseMedium.getSource()).
            startTime(baseMedium.getStartTime()).duration(baseMedium.getDuration()).build();


  }

}
