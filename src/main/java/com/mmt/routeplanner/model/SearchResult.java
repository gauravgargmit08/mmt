package com.mmt.routeplanner.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class SearchResult {
  @ApiModelProperty(name = "destination", value = "Destination to Search for",required = true,example = "A,B,C,Delhi,Hyderabad")
  private String destination;
  @ApiModelProperty(name = "source", value = "source to Search for",required = true,example = "A,B,C,Delhi,Hyderabad")
  private String source;
  @ApiModelProperty(name = "routeSort", value = "CHEAPEST,SHORTEST",required = true,example = "CHEAPEST,SHORTEST")
  private String routeSort;
  @ApiModelProperty(name = "searchDate", value = "searchDate date to be in format of yyyy-MM-dd",required = true,example = "2021-01-08")
  private Date searchDate;
  private List<Routes> routes;
  private String message;
}
