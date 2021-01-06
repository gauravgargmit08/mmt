package com.mmt.routeplanner.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;


@Data
public class FlightEvent {

  private String from;
  private String to;
  @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="IST")
  private Date date;
  private long duration;
  private BigDecimal fare;
  private String startTime;
  private String flight_Id;

}
