package com.mmt.routeplanner.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
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

  @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd", timezone="IST")
  private Date flightDate;
  @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="IST")
  private Date startDateTime;
  private long duration;
  private BigDecimal fare;
  private String startTime;
  @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="IST")
  private Date endDate;


}
