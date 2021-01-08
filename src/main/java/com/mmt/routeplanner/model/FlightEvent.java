package com.mmt.routeplanner.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;


@Data
public class FlightEvent {

  @ApiModelProperty(name = "to", value = "Source",required = true,example = "A,B,C,Delhi,Hyderabad")
  private String from;
  @ApiModelProperty(name = "from", value = "Destination",required = true,example = "A,B,C,Delhi,Hyderabad")
  private String to;

  @ApiModelProperty(name = "searchDate", value = "searchDate date to be in format of yyyy-MM-dd",required = true,example = "2021-01-08")
  @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd", timezone="IST")
  private Date date;

  @ApiModelProperty(name = "duration", value = "60",required = true,example = "60,120,145",notes = "In Minutes")
  private long duration;

  @ApiModelProperty(name = "fare", value = "60",required = true,example = "60,120,145",notes = "In INR")
  private BigDecimal fare;

  @ApiModelProperty(name = "startTime", value = "18:00",required = true,example = "18:00,12:00",notes = "24 Hrs")
  private String startTime;

  @ApiModelProperty(name = "flight_Id", value = "flight_Id",required = true,example = "Random String",notes = "Random String")
  private String flight_Id;

}
