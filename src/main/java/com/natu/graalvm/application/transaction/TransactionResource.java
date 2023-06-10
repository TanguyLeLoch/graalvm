package com.natu.graalvm.application.transaction;

import com.natu.graalvm.domain.Transaction;


public class TransactionResource {
    Transaction transaction;

    public TransactionResource(Transaction transaction) {
        this.transaction = transaction;
    }

    public String getHash() {
        return transaction.getHash();
    }


}