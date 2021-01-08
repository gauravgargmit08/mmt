package com.mmt.routeplanner.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Transfer {
  @ApiModelProperty(name = "source", value = "source to Search for",required = true,example = "A,B,C,Delhi,Hyderabad")
  private String source;
  @ApiModelProperty(name = "destination", value = "Destination to Search for",required = true,example = "A,B,C,Delhi,Hyderabad")
  private String destination;
  @ApiModelProperty(name = "Code for the Medium ", value = "Flight Id or Bus Id",required = true,example = "Randon string")
  private String code;
  @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd", timezone="IST")
  private Date startDate;
  @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="IST")
  private Date startDateTime;
  @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="IST")
  private Date endDateTime;
  private long duration;
  private BigDecimal fare;
  private String startTime;

  private String type;
  private String mediumId;

}
