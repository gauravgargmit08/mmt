package com.mmt.routeplanner.scheduler;

import com.mmt.routeplanner.graph.GraphT;
import com.mmt.routeplanner.graph.Medium;
import com.mmt.routeplanner.model.FlightEvent;
import com.mmt.routeplanner.model.SearchResult;
import com.mmt.routeplanner.service.CreateRoute;
import com.mmt.routeplanner.service.EventRouter;
import com.mmt.routeplanner.util.RouteUtil;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import org.jgrapht.GraphPath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class DataGen {

  @Autowired
  private EventRouter  eventRouter;

  @Autowired
  private CreateRoute createRoute;

  @Scheduled(fixedDelay = 1000000000)
  public void generateData(){



    FlightEvent flight = new FlightEvent();
    flight.setFlight_Id(UUID.randomUUID().toString());
    flight.setFrom("A");
    flight.setTo("B");
    flight.setFare(BigDecimal.TEN);
    flight.setDate(RouteUtil.getDateWithoutTimeUsingCalendar(new Date()));
    flight.setDuration(2);
    flight.setStartTime("12:00");
    flight.setFare(BigDecimal.TEN);
    eventRouter.processEvent(flight);


    flight = new FlightEvent();
    flight.setFlight_Id(UUID.randomUUID().toString());
    flight.setFrom("B");
    flight.setTo("C");
    flight.setStartTime("15:00");
    flight.setFare(BigDecimal.valueOf(20L));
    flight.setDuration(2);
    flight.setDate(RouteUtil.getDateWithoutTimeUsingCalendar(new Date()));
    eventRouter.processEvent(flight);



    flight = new FlightEvent();
    flight.setFlight_Id(UUID.randomUUID().toString());
    flight.setFrom("C");
    flight.setTo("D");
    flight.setStartTime("18:00");
    flight.setFare(BigDecimal.valueOf(120L));
    flight.setDuration(2);
    flight.setDate(RouteUtil.getDateWithoutTimeUsingCalendar(new Date()));
    eventRouter.processEvent(flight);



    flight = new FlightEvent();
    flight.setFlight_Id(UUID.randomUUID().toString());
    flight.setTo("D");
    flight.setDuration(4);
    flight.setFrom("A");
    flight.setStartTime("18:00");
    flight.setFare(BigDecimal.valueOf(30L));
    flight.setDate(RouteUtil.getDateWithoutTimeUsingCalendar(new Date()));
    eventRouter.processEvent(flight);


    flight = new FlightEvent();
    flight.setFlight_Id(UUID.randomUUID().toString());
    flight.setFrom("B");
    flight.setTo("D");
    flight.setStartTime("18:00");
    flight.setFare(BigDecimal.valueOf(25L));
    flight.setDuration(2);
    flight.setDate(RouteUtil.getDateWithoutTimeUsingCalendar(new Date()));
    eventRouter.processEvent(flight);


    List<GraphPath<String, Medium>> graphs = GraphT.defaultGetPath("A","D");
    System.out.println("Paths "+ graphs);

    SearchResult searchResult = createRoute.searchRoutesCheapest(graphs,RouteUtil.getDateWithoutTimeUsingCalendar(new Date()),"A","D");

  }

}
