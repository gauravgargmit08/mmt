package com.mmt.routeplanner.service;

import com.mmt.routeplanner.entity.BaseMedium;
import com.mmt.routeplanner.entity.Flight;
import java.util.Date;
import java.util.Optional;

public interface IMediumService {
  Optional<? extends BaseMedium> findCheapestFlightsBySrcAndByDestAndByDate(String src, String dest,
      Date startDate, Date endDate);
  Optional<? extends BaseMedium> findBySourceAndDestinationAndByDate(String src, String dest,
      Date startDate, Date endDate);
}
