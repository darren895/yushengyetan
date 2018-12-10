package com.da.jubensha.repository.impl;

import com.da.jubensha.domain.Evidence;
import com.da.jubensha.repository.EvidenceExtRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

public class EvidenceExtRepositoryImpl implements EvidenceExtRepository {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public Evidence chooseEvi(int id) {
        Query query = new Query(Criteria.where("id").is(id).and("state").is(true));
        Update update = new Update();
        update.set("state", false);
        FindAndModifyOptions options = new FindAndModifyOptions();
        options.returnNew(false);
        Evidence evidence = this.mongoTemplate.findAndModify(query,update, options, Evidence.class);
        return evidence;
    }
}
