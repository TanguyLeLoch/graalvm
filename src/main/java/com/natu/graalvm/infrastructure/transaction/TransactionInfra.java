package com.natu.graalvm.infrastructure.transaction;

import com.natu.graalvm.domain.Transaction;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "transaction")
public class TransactionInfra {
    @Id
    String hash;

    public TransactionInfra(Transaction transaction) {
        this.hash = transaction.getHash();
    }

    @PersistenceCreator
    public TransactionInfra(String hash) {
        this.hash = hash;
    }

    public Transaction toDomain() {
        return new Transaction(hash);
    }
}
