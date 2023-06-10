package com.natu.graalvm.presentation.transaction;

import com.natu.graalvm.application.transaction.TransactionResource;

public class TransactionResponse {

    TransactionResource transactionResource;

    TransactionResponse(TransactionResource transactionResource) {
        this.transactionResource = transactionResource;
    }

    TransactionResponse() {
        // for jackson
    }

    public String getHash() {
        return transactionResource.getHash();
    }


}
