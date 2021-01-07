package com.mmt.routeplanner.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

@Data
public class BusEvent {

  private String from;
  private String to;
  @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd", timezone="IST")
  private Date date;
  private long duration;
  private BigDecimal fare;
  private String startTime;
  private String bus_id;

}
