package com.natu.graalvm.domain.transaction.infrastructure;

import com.natu.graalvm.domain.transaction.core.model.Transaction;
import com.natu.graalvm.domain.transaction.core.port.outgoing.TransactionDatabase;

import java.util.Optional;

public class TransactionDatabaseAdapter implements TransactionDatabase {

    private final TransactionRepository repository;

    public TransactionDatabaseAdapter(TransactionRepository repository) {
        this.repository = repository;
    }

    @Override
    public Transaction save(Transaction transaction) {
        return repository.insert(transaction);
    }

    @Override
    public Optional<Transaction> findByHash(String hash) {
        return repository.findByHash(hash);
    }
}
