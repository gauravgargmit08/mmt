package com.mmt.routeplanner.repo;

import com.mmt.routeplanner.entity.Buses;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BusesRepository extends CrudRepository<Buses,String> {

}
