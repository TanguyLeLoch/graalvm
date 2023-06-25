package com.natu.graalvm.domain.transaction.core.port.incomming;

import com.natu.graalvm.domain.transaction.application.ProfitRequest;
import com.natu.graalvm.domain.transaction.core.model.Transaction;

public interface RetrieveTransaction {
    Transaction handle(String hash);

    void handleRequest(ProfitRequest request);
}
