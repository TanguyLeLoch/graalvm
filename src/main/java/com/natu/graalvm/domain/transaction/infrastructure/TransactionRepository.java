package com.natu.graalvm.domain.transaction.infrastructure;

import com.natu.graalvm.domain.transaction.core.model.Transaction;
import com.natu.graalvm.domain.transaction.core.model.TransactionInfraMongo;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TransactionRepository {

    private final MongoTemplate mongoTemplate;

    public TransactionRepository(MongoTemplate mongoTemplate) {
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

    public List<Transaction> findByFromOrTo(String address) {
        Criteria from = Criteria.where("from").is(address);
        Criteria to = Criteria.where("to").is(address);
        Query query = Query.query(new Criteria().orOperator(from, to));
        List<TransactionInfraMongo> transactionInfraMongoList = mongoTemplate.find(query, TransactionInfraMongo.class);
        List<Transaction> transactions = new ArrayList<>(transactionInfraMongoList.stream()
                .map(TransactionInfraMongo::toDomain)
                .toList());
        transactions.sort((t1, t2) -> Math.toIntExact(t2.getBlockNumber() - t1.getBlockNumber()));
        return transactions;
    }
}
