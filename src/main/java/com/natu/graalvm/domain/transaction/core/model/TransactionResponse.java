package com.natu.graalvm.domain.transaction.core.model;

public class TransactionResponse {

    Transaction transaction;

    public TransactionResponse(Transaction transaction) {
        this.transaction = transaction;
    }

    TransactionResponse() {
        // for jackson
    }

    public String getHash() {
        return transaction.getHash();
    }


}
