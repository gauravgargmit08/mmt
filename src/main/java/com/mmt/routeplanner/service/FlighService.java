package com.mmt.routeplanner.service;

import com.mmt.routeplanner.entity.Flight;
import com.mmt.routeplanner.repo.FlightRepository;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FlighService {

  @Autowired
  private FlightRepository flightRepository;

  public List<Flight> findFlightsBySrcAndByDestAndByDate(String src,String dest, Date date){

    List<Flight>  flights = flightRepository.findAllById()

  }

}
