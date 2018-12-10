package com.da.jubensha.repository;

import com.da.jubensha.domain.Drama;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface DramaRepository extends MongoRepository<Drama, Integer> {

    @Query(value = "{roleId:?0,step:{$lte:?1}}", sort = "{_id:1}")
    List<Drama> findWithStep(Integer roleId, Integer step);
}
