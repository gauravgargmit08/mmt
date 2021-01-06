package com.mmt.routeplanner.service;

import com.mmt.routeplanner.entity.Bus;
import com.mmt.routeplanner.repo.BusesRepository;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class BusService implements IMediumService {

  @Autowired
  private BusesRepository busesRepository;

  @Override
  public Optional<Bus> findCheapestFlightsBySrcAndByDestAndByDate(String src, String dest,
      Date startDate, Date endDate) {
    List<Bus> flights = busesRepository
        .findBySourceAndDestinationAndStartDateTimeBetweenOrderByFareAsc(src, dest,
            startDate, endDate);
    if (CollectionUtils.isEmpty(flights)) {
      return Optional.empty();
    } else {
      return Optional.of(flights.get(0));
    }

  }

  @Override
  public Optional<Bus> findBySourceAndDestinationAndByDate(String src, String dest,
      Date startDate, Date endDate) {
    List<Bus> flights = busesRepository
        .findBySourceAndDestinationAndStartDateTimeBetweenOrderByDurationAsc(src, dest,
            startDate, endDate);
    if (CollectionUtils.isEmpty(flights)) {
      return Optional.empty();
    } else {
      return Optional.of(flights.get(0));
    }

  }

}
