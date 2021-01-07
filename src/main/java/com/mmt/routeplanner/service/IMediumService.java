package com.mmt.routeplanner.service;

import com.mmt.routeplanner.entity.BaseMedium;
import java.util.Date;
import java.util.Optional;

public interface IMediumService {
  Optional<? extends BaseMedium> findCheapestBySrcAndByDestAndByDate(String src, String dest,
      Date startDate, Date endDate);
  Optional<? extends BaseMedium> findShortestBySourceAndDestinationAndByDate(String src, String dest,
      Date startDate, Date endDate);
}
