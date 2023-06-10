package com.natu.graalvm.domain;

public class Transaction {
    String hash;

    public Transaction(String hash) {
        this.hash = hash;
    }

    public String getHash() {
        return hash;
    }
}
