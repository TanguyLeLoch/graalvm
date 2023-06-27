package com.natu.graalvm.domain.user.core.model;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class Balance {
    private final String tokenAddress;
    private final List<Operation> operations;

    Balance(String tokenAddress, List<Operation> operations) {
        this.tokenAddress = tokenAddress;
        this.operations = operations;
    }

    public Balance(String tokenAddress) {
        this.tokenAddress = tokenAddress;
        this.operations = new ArrayList<>();
    }

    public BigInteger getBalance() {
        BigInteger balance = BigInteger.ZERO;
        for (Operation operation : operations) {
            balance = balance.add(operation.getAmount());
        }
        return balance;
    }

    public void addAmount(BigInteger amount, long timestamp) {
        operations.add(new Operation(amount, timestamp));
    }

    public String getTokenAddress() {
        return tokenAddress;
    }


    class Operation {
        private final BigInteger amount;
        private final long timestamp;

        public Operation(BigInteger amount, long timestamp) {
            this.amount = amount;
            this.timestamp = timestamp;
        }

        public BigInteger getAmount() {
            return amount;
        }
    }
}
