package com.da.jubensha.repository;

import com.da.jubensha.domain.UserRole;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRoleRepository extends UserRoleExtRepository, MongoRepository<UserRole, String> {

    UserRole findByRoleId(Integer roleId);
}
