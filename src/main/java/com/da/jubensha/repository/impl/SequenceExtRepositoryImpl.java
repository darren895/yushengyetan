package com.da.jubensha.repository.impl;

import com.da.jubensha.domain.Sequence;
import com.da.jubensha.repository.SequenceExtRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

public class SequenceExtRepositoryImpl implements SequenceExtRepository {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public long getId(String key) {
        Query query = new Query(Criteria.where("id").is(key));
        Update update = new Update();
        update.inc("value", 1);
        FindAndModifyOptions options = new FindAndModifyOptions();
        options.upsert(true);
        options.returnNew(true);
        Sequence seqId = mongoTemplate.findAndModify(query, update, options, Sequence.class);
        return seqId.getValue();
    }
}
