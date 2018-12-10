package com.da.jubensha.repository;

import com.da.jubensha.domain.Step;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface StepRepository extends MongoRepository<Step, Integer> {
}
