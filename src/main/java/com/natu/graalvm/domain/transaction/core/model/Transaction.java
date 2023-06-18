package com.natu.graalvm.domain.transaction.core.model;

import lombok.Getter;

@Getter
public class Transaction {
    String hash;
    String from;
    String to;
    long blockNumber;

    public Transaction(String hash, String from, String to, long blockNumber) {
        this.hash = hash;
        this.from = from;
        this.to = to;
        this.blockNumber = blockNumber;
    }
}
