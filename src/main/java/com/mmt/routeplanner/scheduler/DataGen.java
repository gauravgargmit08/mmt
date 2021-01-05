package com.mmt.routeplanner.scheduler;

import com.mmt.routeplanner.entity.Flight;
import com.mmt.routeplanner.repo.FlightRepository;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class DataGen {

  @Autowired
  private FlightRepository flightRepository;

  @Scheduled(fixedDelay = 1000000000)
  public void generateData(){

    Flight flight = new Flight();
    flight.setFlight_Id(UUID.randomUUID().toString());
    flight.setDestination("A");
    flight.setSource("B");
    flight.setFare(BigDecimal.TEN);
    flight.setStartDate(LocalDateTime.now().plusDays(1));
    flight.setDuration(2);
    flight.setStartTime("12:00");

    flightRepository.save(flight);

    flight = new Flight();
    flight.setFlight_Id(UUID.randomUUID().toString());
    flight.setDestination("B");
    flight.setSource("C");
    flight.setStartTime("15:00");
    flightRepository.save(flight);


    flight = new Flight();
    flight.setFlight_Id(UUID.randomUUID().toString());
    flight.setDestination("C");
    flight.setSource("D");
    flight.setStartTime("18:00");
    flightRepository.save(flight);


    flight = new Flight();
    flight.setFlight_Id(UUID.randomUUID().toString());
    flight.setDestination("A");
    flight.setDuration(4);
    flight.setSource("D");
    flight.setStartTime("18:00");
    flightRepository.save(flight);

    List<Flight> flights = flightRepository.findAll();
    System.out.println("flights" + flights.toString());


  }

}
