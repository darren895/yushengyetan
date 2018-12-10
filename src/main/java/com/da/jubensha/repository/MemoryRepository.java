package com.da.jubensha.repository;

import com.da.jubensha.domain.Memory;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MemoryRepository extends MongoRepository<Memory, Integer> {

    List<Memory> findByState(boolean state);
}
