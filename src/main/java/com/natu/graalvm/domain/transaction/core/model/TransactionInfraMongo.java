package com.natu.graalvm.domain.transaction.core.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "transaction")
public class TransactionInfraMongo {
    @Id
    String hash;

    public TransactionInfraMongo(Transaction transaction) {
        this.hash = transaction.getHash();
    }

    @PersistenceCreator
    public TransactionInfraMongo(String hash) {
        this.hash = hash;
    }

    public Transaction toDomain() {
        return new Transaction(hash);
    }
}
