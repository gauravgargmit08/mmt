package com.mmt.routeplanner.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SearchResult {
  private String destination;
  private String source;
  private Date searchDate;
  private BigDecimal totalFare;
  private List<Route> routes;
}
