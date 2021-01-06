package com.mmt.routeplanner.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "Flights")
@Data
public class Flight extends BaseMedium {

  private String flight_Id;



}
