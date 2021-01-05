package com.mmt.routeplanner.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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
  private String destination;
  private LocalDateTime startDate;
  private long duration;
  private BigDecimal fare;
  private String startTime;
  private LocalDateTime endDate;


}
