package com.mmt.routeplanner.service;

import com.mmt.routeplanner.entity.Flight;
import com.mmt.routeplanner.graph.GraphT;
import com.mmt.routeplanner.model.FlightEvent;
import com.mmt.routeplanner.repo.FlightRepository;
import com.mmt.routeplanner.util.RouteUtil;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventRouter {

  @Autowired
  private FlightRepository flightRepository;

  public void processEvent(FlightEvent flightEvent){
    Flight flight = new Flight();
    flight.setFlight_Id(flightEvent.getFlight_Id());
    flight.setSource(flightEvent.getFrom());
    flight.setDestination(flightEvent.getTo());
    flight.setFare(flightEvent.getFare());
    flight.setFlightDate(flightEvent.getDate());
    flight.setStartDate(RouteUtil.getDateTime(flightEvent.getDate(),flightEvent.getStartTime()));
    flight.setEndDate(RouteUtil.addHours(flight.getStartDate(),flightEvent.getDuration()));
    flight.setDuration(flightEvent.getDuration());
    flightRepository.save(flight);
    GraphT.addRoute(flightEvent.getFrom(),flightEvent.getTo(),"FLIGHT");
    List<Flight> flights = flightRepository.findAll();
    System.out.println("flights" + flights.toString());

  }

}
