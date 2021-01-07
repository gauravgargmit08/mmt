package com.mmt.routeplanner.service;

import com.mmt.routeplanner.entity.Bus;
import com.mmt.routeplanner.entity.Flight;
import com.mmt.routeplanner.graph.GraphT;
import com.mmt.routeplanner.model.BusEvent;
import com.mmt.routeplanner.model.FlightEvent;
import com.mmt.routeplanner.repo.BusesRepository;
import com.mmt.routeplanner.repo.FlightRepository;
import com.mmt.routeplanner.util.MediumType;
import com.mmt.routeplanner.util.RouteUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EventRouter {

  @Autowired
  private FlightRepository flightRepository;

  @Autowired
  private BusesRepository busesRepository;

  public void processEvent(FlightEvent flightEvent){
    Flight flight = new Flight();
    flight.setFlight_Id(flightEvent.getFlight_Id());
    flight.setSource(flightEvent.getFrom());
    flight.setDestination(flightEvent.getTo());
    flight.setFare(flightEvent.getFare());
    flight.setFlightDate(flightEvent.getDate());
    flight.setStartDateTime(RouteUtil.getDateTime(flightEvent.getDate(),flightEvent.getStartTime()));
    flight.setEndDate(RouteUtil.addHours(flight.getStartDateTime(),flightEvent.getDuration()));
    flight.setDuration(flightEvent.getDuration());
    flight.setStartTime(flightEvent.getStartTime());

    flightRepository.save(flight);
    log.info("Saving flight: {}" , flight.toString());
    GraphT.addRoute(flightEvent.getFrom(),flightEvent.getTo(), MediumType.MEDIUM_FLIGHT);
  }

  public void processEvent(BusEvent busEvent){
    Bus bus = new Bus();
    bus.setBus_Id(busEvent.getBus_id());
    bus.setSource(busEvent.getFrom());
    bus.setDestination(busEvent.getTo());
    bus.setFare(busEvent.getFare());
    bus.setFlightDate(busEvent.getDate());
    bus.setStartDateTime(RouteUtil.getDateTime(busEvent.getDate(),busEvent.getStartTime()));
    bus.setEndDate(RouteUtil.addHours(bus.getStartDateTime(),busEvent.getDuration()));
    bus.setDuration(busEvent.getDuration());
    bus.setStartTime(busEvent.getStartTime());
    busesRepository.save(bus);
    log.info("Saving bus: {}" , bus.toString());
    GraphT.addRoute(busEvent.getFrom(),busEvent.getTo(),MediumType.MEDIUM_BUS);
  }

}
