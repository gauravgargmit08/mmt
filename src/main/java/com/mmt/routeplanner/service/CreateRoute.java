package com.mmt.routeplanner.service;

import com.mmt.routeplanner.graph.Medium;
import com.mmt.routeplanner.model.Route;
import java.util.Collections;
import java.util.List;
import org.jgrapht.GraphPath;
import org.jgrapht.graph.GraphWalk;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateRoute {

  @Autowired
  private FlightService flightService;

  public List<Route> allRoutes( List<GraphPath<String, Medium>> graphs){

    graphs.forEach(stringMediumGraphPath -> {
      GraphWalk graphWalk = (GraphWalk) stringMediumGraphPath.getEdgeList();
      System.out.println(graphWalk.toString());

    });
    return Collections.emptyList();

  }

}
