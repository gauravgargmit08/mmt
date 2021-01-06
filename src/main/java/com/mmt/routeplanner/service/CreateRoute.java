package com.mmt.routeplanner.service;

import com.mmt.routeplanner.entity.BaseMedium;
import com.mmt.routeplanner.factory.MediumServiceFactory;
import com.mmt.routeplanner.graph.Medium;
import com.mmt.routeplanner.model.Route;
import com.mmt.routeplanner.util.RouteUtil;
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

  public List<Route> listRoutes(List<GraphPath<String, Medium>> graphs, Date startDate) {
    graphs.forEach(stringMediumGraphPath -> {
      List<Medium> graphWalk = stringMediumGraphPath.getEdgeList();
      System.out.println(graphWalk.toString());
      int i = 0;
      Date nextDate = RouteUtil.atStartOfDay(startDate);
      Date endDate = RouteUtil.atEndOfDay(startDate);

      for (Medium medium : graphWalk) {
        String[] strArray = medium.getType().split(RouteUtil.DELIMETER);
        IMediumService mediumService =
            mediumServiceFactory.factory(strArray[2]);
        Optional<? extends BaseMedium> baseMedium = mediumService.
            findCheapestFlightsBySrcAndByDestAndByDate(strArray[0], strArray[1], nextDate, endDate);
        baseMedium.isPresent();

      }


    });
    return Collections.emptyList();

  }

}
