package com.da.jubensha.repository.impl;

import com.da.jubensha.domain.UserRole;
import com.da.jubensha.repository.UserRoleExtRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

public class UserRoleExtRepositoryImpl implements UserRoleExtRepository {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public UserRole descFirst(Integer roleId) {
        Query query = new Query(Criteria.where("roleId").is(roleId));
        Update update = new Update();
        update.inc("firstPoint", -1);
        FindAndModifyOptions options = new FindAndModifyOptions();
        options.upsert(false);
        options.returnNew(true);
        UserRole userRole = this.mongoTemplate.findAndModify(query, update, options, UserRole.class);
        return userRole;
    }

    @Override
    public UserRole descSecond(Integer roleId) {
        Query query = new Query(Criteria.where("roleId").is(roleId));
        Update update = new Update();
        update.inc("secondPoint", -1);
        FindAndModifyOptions options = new FindAndModifyOptions();
        options.upsert(false);
        options.returnNew(true);
        UserRole userRole = this.mongoTemplate.findAndModify(query, update, options, UserRole.class);
        return userRole;
    }

    @Override
    public long deleteBySessionId(String sessionId) {
        Query query = new Query(Criteria.where("sessonId").is(sessionId));
        return this.mongoTemplate.remove(query, UserRole.class).getDeletedCount();
    }

    @Override
    public long deleteByRoleId(Integer roleId) {
        Query query = new Query(Criteria.where("roleId").is(roleId));
        return this.mongoTemplate.remove(query, UserRole.class).getDeletedCount();
    }
}
