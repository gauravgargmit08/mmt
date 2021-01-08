package com.mmt.routeplanner.factory;

import static com.mmt.routeplanner.util.MediumType.MEDIUM_BUS;
import static com.mmt.routeplanner.util.MediumType.MEDIUM_FLIGHT;

import com.mmt.routeplanner.service.BusService;
import com.mmt.routeplanner.service.FlightService;
import com.mmt.routeplanner.service.IMediumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MediumServiceFactory {

  @Autowired
  private FlightService flightService;

  @Autowired
  private BusService busService;

  public IMediumService factory(String medium) {

    switch (medium) {
      case MEDIUM_FLIGHT:
        return flightService;
      case MEDIUM_BUS:
        return busService;
      default:
        throw new IllegalArgumentException("Medium not supported");

    }

  }


}
