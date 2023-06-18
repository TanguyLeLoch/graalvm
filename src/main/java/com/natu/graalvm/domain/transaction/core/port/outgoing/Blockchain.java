package com.natu.graalvm.domain.transaction.core.port.outgoing;

import com.natu.graalvm.domain.transaction.core.model.Transaction;

import java.util.List;

public interface Blockchain {
    List<Transaction> getTransactions(String address, long fromBlock, long toBlock);
}
