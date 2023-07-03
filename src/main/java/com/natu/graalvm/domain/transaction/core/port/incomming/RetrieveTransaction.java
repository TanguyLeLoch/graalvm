package com.natu.graalvm.domain.transaction.core.port.incomming;

import com.natu.graalvm.domain.transaction.core.model.Transaction;

import java.util.List;

public interface RetrieveTransaction {
    Transaction handle(String hash);

    List<Transaction> ofUser(String userAddress);

//    List<Transaction> handleRequest(ProfitRequest request);
}
