package com.da.jubensha.repository;

import com.da.jubensha.domain.UserEvidence;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface UserEvidenceRepository extends UserEvidenceExtRepository, MongoRepository<UserEvidence, Integer> {

    @Query(value = "{placeId:?0,roleId:?1}", sort = "{_id:1}")
    List<UserEvidence> findByPlaceAndRole(Integer placeId, Integer roleId);
}
