package com.mmt.routeplanner.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Routes {
  @ApiModelProperty(name = "totalFare", value = "totalFare for this route in INR",required = true,example = "30")
  private BigDecimal totalFare;
  @ApiModelProperty(name = "totalDuration", value = "Total Travelling in mins",required = true,example = "120")
  private long totalDuration;
  private List<Transfer> transfers;
}
