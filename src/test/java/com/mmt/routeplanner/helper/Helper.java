package com.mmt.routeplanner.helper;

import com.mmt.routeplanner.entity.BaseMedium;
import com.mmt.routeplanner.entity.Bus;
import com.mmt.routeplanner.entity.Flight;
import com.mmt.routeplanner.util.RouteUtil;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

public class Helper {

  public static Optional<Flight> flight(String source, String destination,
      BigDecimal fare, Date date, String startTime, long duration) {

    Flight flight = new Flight();
    flight.setSource(source);
    flight.setDestination(destination);
    flight.setFare(fare);
    flight.setFlightDate(RouteUtil.getDateWithoutTimeUsingCalendar(date));
    flight.setFlight_Id(UUID.randomUUID().toString());
    flight.setStartDateTime(RouteUtil.getDateTime(flight.getFlightDate(), startTime));
    flight.setEndDate(RouteUtil.addHours(flight.getStartDateTime(), duration));
    flight.setDuration(duration);
    flight.setStartTime(startTime);
    return Optional.of(flight);

  }

  public static Optional<Bus> bus(String source, String destination,
      BigDecimal fare, Date date, String startTime, long duration) {
    Bus bus = new Bus();
    bus.setSource(source);
    bus.setDestination(destination);
    bus.setFare(fare);
    bus.setFlightDate(date);
    bus.setBus_Id(UUID.randomUUID().toString());
    bus.setStartDateTime(RouteUtil.getDateTime(bus.getFlightDate(), startTime));
    bus.setEndDate(RouteUtil.addHours(bus.getStartDateTime(), duration));
    bus.setDuration(duration);
    bus.setStartTime(startTime);
    return Optional.of(bus);

  }


}
