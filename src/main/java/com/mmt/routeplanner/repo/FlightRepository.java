package com.mmt.routeplanner.repo;

import com.mmt.routeplanner.entity.Flight;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlightRepository extends CrudRepository<Flight,String> {
  List<Flight> findAll();
  List<Flight> findBySourceAndDestinationAndEffctDateAfterAndExpDateBefore();

}
