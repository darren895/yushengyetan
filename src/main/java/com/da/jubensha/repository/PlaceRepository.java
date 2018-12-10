package com.da.jubensha.repository;

import com.da.jubensha.domain.Place;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface PlaceRepository extends MongoRepository<Place, Integer> {

    @Query(value = "{firstPlace:true}", sort = "{_id:1}")
    List<Place> findFirstStep();

    @Query(value = "{secondPlace:true}", sort = "{_id:1}")
    List<Place> findSecondStep();
}
