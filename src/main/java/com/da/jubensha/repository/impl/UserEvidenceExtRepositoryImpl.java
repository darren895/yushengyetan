package com.da.jubensha.repository.impl;

import com.da.jubensha.domain.UserEvidence;
import com.da.jubensha.repository.UserEvidenceExtRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

public class UserEvidenceExtRepositoryImpl implements UserEvidenceExtRepository {

    @Autowired
    private MongoTemplate mongoTemplate;
    @Override
    public UserEvidence changeEvidence(Integer id, Integer toRoleId, Integer roleId) {
        Query query = new Query(Criteria.where("id").is(id).and("roleId").is(roleId));
        Update update = new Update();
        update.set("roleId", toRoleId);
        FindAndModifyOptions options = new FindAndModifyOptions();
        options.returnNew(true);
        UserEvidence userEvidence = this.mongoTemplate.findAndModify(query, update, options, UserEvidence.class);
        return userEvidence;
    }

    @Override
    public int countPlace(Integer placeId, Integer roleId, Integer step) {
        Query query = new Query(Criteria.where("roleId").is(roleId).and("placeId").is(placeId).and("step").is(step));
        Long count = this.mongoTemplate.count(query, UserEvidence.class);
        return count.intValue();
    }
}
