package com.mmt.routeplanner.model;

import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Transfer {

  @ApiModelProperty(name = "destination", value = "Destination to Search for",required = true,example = "A,B,C,Delhi,Hyderabad")
  private String destination;
  @ApiModelProperty(name = "source", value = "source to Search for",required = true,example = "A,B,C,Delhi,Hyderabad")
  private String source;
  @ApiModelProperty(name = "Code for the Medium ", value = "Flight Id or Bus Id",required = true,example = "Randon string")
  private String code;
  private Date startDate;
  private Date startDateTime;
  private long duration;
  private BigDecimal fare;
  private String startTime;
  private Date endDate;
  private String type;
  private String mediumId;

}
