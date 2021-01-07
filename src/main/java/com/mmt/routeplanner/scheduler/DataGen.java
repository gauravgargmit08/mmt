package com.mmt.routeplanner.scheduler;

import com.google.gson.Gson;
import com.mmt.routeplanner.graph.GraphT;
import com.mmt.routeplanner.graph.Medium;
import com.mmt.routeplanner.model.BusEvent;
import com.mmt.routeplanner.model.FlightEvent;
import com.mmt.routeplanner.model.SearchResult;
import com.mmt.routeplanner.service.SearchRoute;
import com.mmt.routeplanner.service.EventRouter;
import com.mmt.routeplanner.util.RouteUtil;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.jgrapht.GraphPath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty(value = "scheduler.enable", havingValue = "true", matchIfMissing = true)
@Slf4j
public class DataGen {

  @Autowired
  private EventRouter  eventRouter;

  @Autowired
  private SearchRoute searchRoute;

  @Scheduled(fixedDelay = 1000000000)
  public void generateData(){



    FlightEvent flight = new FlightEvent();
    flight.setFlight_Id(UUID.randomUUID().toString());
    flight.setFrom("A");
    flight.setTo("B");
    flight.setFare(BigDecimal.TEN);
    flight.setDate(RouteUtil.getDateWithoutTimeUsingCalendar(new Date()));
    flight.setDuration(120);
    flight.setStartTime("12:00");
    flight.setFare(BigDecimal.TEN);
    eventRouter.processEvent(flight);


    flight = new FlightEvent();
    flight.setFlight_Id(UUID.randomUUID().toString());
    flight.setFrom("B");
    flight.setTo("C");
    flight.setStartTime("15:00");
    flight.setFare(BigDecimal.valueOf(20L));
    flight.setDuration(120);
    flight.setDate(RouteUtil.getDateWithoutTimeUsingCalendar(new Date()));
    eventRouter.processEvent(flight);



    flight = new FlightEvent();
    flight.setFlight_Id(UUID.randomUUID().toString());
    flight.setFrom("C");
    flight.setTo("D");
    flight.setStartTime("18:00");
    flight.setFare(BigDecimal.valueOf(120L));
    flight.setDuration(120);
    flight.setDate(RouteUtil.getDateWithoutTimeUsingCalendar(new Date()));
    eventRouter.processEvent(flight);



    flight = new FlightEvent();
    flight.setFlight_Id(UUID.randomUUID().toString());
    flight.setTo("D");
    flight.setDuration(180);
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
    flight.setDuration(120);
    flight.setDate(RouteUtil.getDateWithoutTimeUsingCalendar(new Date()));
    eventRouter.processEvent(flight);


    BusEvent busEvent = new BusEvent();
    busEvent.setBus_id(UUID.randomUUID().toString());
    busEvent.setFrom("A");
    busEvent.setTo("B");
    busEvent.setFare(BigDecimal.valueOf(5));
    busEvent.setDate(RouteUtil.getDateWithoutTimeUsingCalendar(new Date()));
    busEvent.setDuration(240);
    busEvent.setStartTime("12:00");
    eventRouter.processEvent(busEvent);


    busEvent = new BusEvent();
    busEvent.setBus_id(UUID.randomUUID().toString());
    busEvent.setFrom("B");
    busEvent.setTo("D");
    busEvent.setFare(BigDecimal.valueOf(1));
    busEvent.setDate(RouteUtil.getDateWithoutTimeUsingCalendar(new Date()));
    busEvent.setDuration(720);
    busEvent.setStartTime("13:00");
    eventRouter.processEvent(busEvent);

    Gson gson = new Gson();
   log.info("json busEvent :{}",gson.toJson(busEvent));
    log.info("json flight :{}",gson.toJson(flight));


    List<GraphPath<String, Medium>> graphs = GraphT.defaultGetPath("A","D");
   log.info("Paths "+ graphs);

    SearchResult searchResult = searchRoute
        .searchRoutes(graphs,RouteUtil.getDateWithoutTimeUsingCalendar(new Date()),"A","D",RouteUtil.SHORTEST);
   log.info(gson.toJson(searchResult));

  }

}
