package com.mmt.routeplanner.model;

import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;


@Data
public class FlightEvent {

  private String from;
  private String to;
  private Date date;
  private long duration;
  private BigDecimal fare;
  private String startTime;
  private String flight_Id;

}
