package com.natu.graalvm.infrastructure.transaction;

import com.natu.graalvm.domain.Transaction;

import java.util.Optional;


public interface TransactionRepository {

    Transaction insert(Transaction transaction);

    Optional<Transaction> findByHash(String hash);
}
