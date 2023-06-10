package com.natu.graalvm.domain.transaction.core.model;

public class Transaction {
    String hash;

    public Transaction(String hash) {
        this.hash = hash;
    }

    public String getHash() {
        return hash;
    }
}
