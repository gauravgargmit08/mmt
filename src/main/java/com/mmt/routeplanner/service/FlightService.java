package com.mmt.routeplanner.service;

import com.mmt.routeplanner.entity.BaseMedium;
import com.mmt.routeplanner.entity.Flight;
import com.mmt.routeplanner.repo.FlightRepository;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class FlightService implements IMediumService{

  @Autowired
  private FlightRepository flightRepository;

  @Override
  public Optional<Flight> findCheapestBySrcAndByDestAndByDate(String src, String dest,
      Date startDate, Date endDate) {
    List<Flight> flights = flightRepository
        .findBySourceAndDestinationAndStartDateTimeBetweenOrderByFareAsc(src, dest,
            startDate, endDate);
    if (CollectionUtils.isEmpty(flights)) {
      return Optional.empty();
    } else {
      return Optional.of(flights.get(0));
    }

  }

  @Override
  public Optional<Flight> findShortestBySourceAndDestinationAndByDate(String src, String dest,
      Date startDate, Date endDate) {
    List<Flight> flights = flightRepository
        .findBySourceAndDestinationAndStartDateTimeBetweenOrderByDurationAsc(src, dest,
            startDate, endDate);
    if (CollectionUtils.isEmpty(flights)) {
      return Optional.empty();
    } else {
      return Optional.of(flights.get(0));
    }

  }

  @Override
  public List<? extends BaseMedium> findAll() {
    return flightRepository.findAll();
  }

}
