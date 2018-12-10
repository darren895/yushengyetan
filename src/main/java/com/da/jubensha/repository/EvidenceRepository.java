package com.da.jubensha.repository;

import com.da.jubensha.domain.Evidence;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface EvidenceRepository extends EvidenceExtRepository,  MongoRepository<Evidence, Integer> {

    @Query(value = "{placeId:?0,state:true}", sort = "{_id:1}")
    List<Evidence> findEvidenceByPlace(Integer placeId);
}
