package com.natu.graalvm.domain.transaction.core.port.outgoing;

import com.natu.graalvm.domain.transaction.core.model.Transaction;

import java.util.List;
import java.util.Optional;

public interface TransactionDatabase {
    Transaction save(Transaction transaction);

    Optional<Transaction> findByHash(String hash);

    List<Transaction> findByUserAddress(String address);
}
