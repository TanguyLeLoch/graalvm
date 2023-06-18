package com.natu.graalvm.domain.transaction.core.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "transaction")
public class TransactionInfraMongo {
    @Id
    String hash;
    String from;
    String to;
    Long blockNumber;

    public TransactionInfraMongo(Transaction transaction) {
        this.hash = transaction.getHash();
        this.from = transaction.getFrom();
        this.to = transaction.getTo();
        this.blockNumber = transaction.getBlockNumber();
    }

    @PersistenceCreator
    public TransactionInfraMongo(String hash, String from, String to, Long blockNumber) {
        this.hash = hash;
        this.from = from;
        this.to = to;
        this.blockNumber = blockNumber;
    }

    public Transaction toDomain() {
        return new Transaction(hash, from, to, blockNumber);
    }
}
