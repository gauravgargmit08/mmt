package com.mmt.routeplanner.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import lombok.Data;


@Data
@MappedSuperclass
public class BaseMedium {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO )
  private long id;

  private String source;

  @Override
  public String toString() {
    return "BaseMedium{" +
        "id=" + id +
        ", source='" + source + '\'' +
        ", destination='" + destination + '\'' +
        ", flightDate=" + flightDate +
        ", startDateTime=" + startDateTime +
        ", duration=" + duration +
        ", fare=" + fare +
        ", startTime='" + startTime + '\'' +
        ", endDate=" + endDate +
        '}';
  }

  private String destination;
  private Date flightDate;
  private Date startDateTime;
  private long duration;
  private BigDecimal fare;
  private String startTime;
  private Date endDate;


}
