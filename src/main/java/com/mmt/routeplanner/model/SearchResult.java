package com.mmt.routeplanner.model;

import java.util.Date;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SearchResult {
  private String destination;
  private String source;
  private String routeSort;
  private Date searchDate;
  private List<Routes> routes;
}
