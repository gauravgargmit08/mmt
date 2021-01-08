package com.mmt.routeplanner.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "Flights")
@Data
public class Flight extends BaseMedium {
  private String flight_Id;

  @Override
  public String toString() {
    return String.format("From [%s] ----> [%s]-------dateTime: [%s] ----------endTime: [%s]-------Fare:[%s]-----Duration:[%d]"
        ,super.getSource(),super.getDestination(),super.getStartDateTime(),super.getEndDate(),super.getFare().toString(),super.getDuration());
  }
}
