package com.mmt.routeplanner.scheduler;

import com.mmt.routeplanner.entity.Flight;
import com.mmt.routeplanner.model.FlightEvent;
import com.mmt.routeplanner.repo.FlightRepository;
import com.mmt.routeplanner.service.EventRouter;
import com.mmt.routeplanner.util.RouteUtil;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class DataGen {

  @Autowired
  private EventRouter  eventRouter;

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
    eventRouter.processEvent(flight);



    flight = new FlightEvent();
    flight.setFlight_Id(UUID.randomUUID().toString());
    flight.setFrom("C");
    flight.setTo("D");
    flight.setStartTime("18:00");
    flight.setFare(BigDecimal.valueOf(120L));
    flight.setDuration(2);
    eventRouter.processEvent(flight);



    flight = new FlightEvent();
    flight.setFlight_Id(UUID.randomUUID().toString());
    flight.setTo("A");
    flight.setDuration(4);
    flight.setFrom("D");
    flight.setStartTime("18:00");
    flight.setFare(BigDecimal.valueOf(30L));
    eventRouter.processEvent(flight);

  }

}
