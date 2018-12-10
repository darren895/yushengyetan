package com.da.jubensha.repository;

import com.da.jubensha.domain.UserEvidence;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface UserEvidenceExtRepository{

    UserEvidence changeEvidence(Integer id, Integer toRoleId, Integer roleId);

    int countPlace(Integer placeId, Integer roleId, Integer step);
}
