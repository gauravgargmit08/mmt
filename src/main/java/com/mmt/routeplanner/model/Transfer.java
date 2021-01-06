package com.mmt.routeplanner.model;

import java.math.BigDecimal;
import java.util.Date;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Transfer {

  private String source;
  private String destination;
  private Date startDate;
  private Date startDateTime;
  private long duration;
  private BigDecimal fare;
  private String startTime;
  private Date endDate;
  private String type;
  private String mediumId;

}
