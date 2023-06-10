package com.natu.graalvm.domain.transaction.core.model;

public class TransactionResponse {

    Transaction transaction;

    public TransactionResponse(Transaction transaction) {
        this.transaction = transaction;
    }

    public String getHash() {
        return transaction.getHash();
    }


}
