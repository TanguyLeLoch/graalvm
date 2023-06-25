package com.natu.graalvm.domain.transaction.core.port.incomming;

import com.natu.graalvm.domain.transaction.core.model.Transaction;

import java.util.List;

public interface AddNewTransaction {
    void fromCsvRecord(List<Transaction> transactions);

    void fromBlockchain(String address);

}
