package com.da.jubensha.repository;

import com.da.jubensha.domain.Role;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface RoleRepository extends RoleExtRepository, MongoRepository<Role, Integer> {

    Role findByName(String name);
}
