package com.natu.graalvm.domain.transaction.infrastructure;

import com.natu.graalvm.domain.transaction.core.model.Transaction;
import com.natu.graalvm.domain.transaction.core.model.TransactionInfraMongo;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class TransactionRepository {

    private final MongoTemplate mongoTemplate;

    TransactionRepository(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public Transaction insert(Transaction transaction) {
        TransactionInfraMongo transactionInfraMongo = new TransactionInfraMongo(transaction);
        TransactionInfraMongo saved = mongoTemplate.insert(transactionInfraMongo);
        return saved.toDomain();
    }

    public Optional<Transaction> findByHash(String hash) {
        TransactionInfraMongo transactionInfraMongo = mongoTemplate.findById(hash, TransactionInfraMongo.class);
        if (transactionInfraMongo != null) {
            return Optional.of(transactionInfraMongo.toDomain());
        } else {
            return Optional.empty();
        }
    }
}
