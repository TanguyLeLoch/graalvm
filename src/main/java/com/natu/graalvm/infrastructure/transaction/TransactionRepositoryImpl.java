package com.natu.graalvm.infrastructure.transaction;

import com.natu.graalvm.domain.Transaction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class TransactionRepositoryImpl implements TransactionRepository {

    private final MongoTemplate mongoTemplate;

    TransactionRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Transaction insert(Transaction transaction) {
        TransactionInfra transactionInfra = new TransactionInfra(transaction);
        TransactionInfra saved = mongoTemplate.insert(transactionInfra);
        return saved.toDomain();
    }

    @Override
    public Optional<Transaction> findByHash(String hash) {
        Query query = Query.query(Criteria.where("_id").is(hash));
        TransactionInfra transactionInfra = mongoTemplate.findOne(query, TransactionInfra.class);
        if (transactionInfra != null) {
            return Optional.of(transactionInfra.toDomain());
        } else {
            return Optional.empty();
        }
    }
}
