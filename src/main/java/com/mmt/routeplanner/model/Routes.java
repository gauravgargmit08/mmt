package com.mmt.routeplanner.model;

import java.math.BigDecimal;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Routes {
  private BigDecimal totalFare;
  private long totalDuration;
  private List<Transfer> transfers;
}
