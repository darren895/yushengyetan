package com.da.jubensha.repository;

import com.da.jubensha.domain.Sequence;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SequenceRepository extends SequenceExtRepository, MongoRepository<Sequence, String> {


}
