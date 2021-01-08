package com.mmt.routeplanner.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SearchRequest {

  @ApiModelProperty(name = "destination", value = "Destination to Search for",required = true,example = "A,B,C,Delhi,Hyderabad")
  @NotNull(message = "destination cannot be null")
  private String destination;

  @ApiModelProperty(name = "source", value = "source to Search for",required = true,example = "A,B,C,Delhi,Hyderabad")
  @NotNull(message = "source cannot be null")
  private String source;

  @ApiModelProperty(name = "routeSort", value = "CHEAPEST,SHORTEST",required = true,example = "CHEAPEST,SHORTEST")
  @NotNull(message = "source cannot be null")
  private String routeSort;

  @NotNull(message = "searchDate cannot be null")
  @ApiModelProperty(name = "searchDate", value = "searchDate date to be in format of yyyy-MM-dd",required = true,example = "2021-01-08")
  @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd", timezone="IST")
  private Date searchDate;
}
