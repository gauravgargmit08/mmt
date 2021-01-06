package com.mmt.routeplanner.repo;

import com.mmt.routeplanner.entity.Bus;
import java.util.Date;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BusesRepository extends CrudRepository<Bus,String> {
  List<Bus> findAll();
  List<Bus> findBySourceAndDestinationAndFlightDateBetweenOrderByFareAsc(String src,String dest, Date startDate,Date endDate);
  List<Bus> findBySourceAndDestinationAndFlightDateBetweenOrderByDurationAsc(String src,String dest, Date startDate,Date endDate);


}
