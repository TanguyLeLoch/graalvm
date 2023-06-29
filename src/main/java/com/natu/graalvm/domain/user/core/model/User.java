package com.natu.graalvm.domain.user.core.model;

import com.natu.graalvm.domain.common.exception.FunctionalException;
import com.natu.graalvm.domain.common.exception.NotFoundException;
import com.natu.graalvm.domain.pair.core.model.Pair;
import com.natu.graalvm.domain.transaction.core.model.Log;
import com.natu.graalvm.domain.transaction.core.model.Transaction;

import java.math.BigInteger;
import java.util.*;

public class User {
    private final String address;
    private final String status;
    private final List<Pair> pairs;
    private final Map<String, Balance> balances;

    public User(String address) {
        this.address = address;
        this.status = "CREATED";
        this.pairs = new ArrayList<>();
        this.balances = new HashMap<>();
    }

    User(String address, String status, List<Pair> pairs, Map<String, Balance> balances) {
        this.address = address;
        this.status = status;
        this.pairs = pairs;
        this.balances = balances;
        checkUniquePair();
    }

    private void checkUniquePair() {
        Set<String> pairAddresses = new HashSet<>();
        for (Pair pair : pairs) {
            if (pairAddresses.contains(pair.getPairAddress())) {
                throw new FunctionalException("Pair address must be unique");
            }
            pairAddresses.add(pair.getPairAddress());
        }
    }

    public String getAddress() {
        return address;
    }

    public String getStatus() {
        return status;
    }

    public List<Pair> getPairs() {
        return Collections.unmodifiableList(pairs);
    }

    public Map<String, Balance> getBalances() {
        return Collections.unmodifiableMap(balances);
    }

    public void addSwapTransactions(List<Transaction> swapTxs) {
        for (Transaction swapTx : swapTxs) {
            List<Log> logs = swapTx.getLogs();
            for (Log log : logs) {
                Pair pair = getPair(log.getAddress());
                addBalance(pair.getToken0().getAddress(), log.getSwap().amount0Out().subtract(log.getSwap().amount0In()), swapTx.getTimestamp());
                addBalance(pair.getToken1().getAddress(), log.getSwap().amount1Out().subtract(log.getSwap().amount1In()), swapTx.getTimestamp());
            }
        }
    }

    private Pair getPair(String address) {
        for (Pair pair : pairs) {
            if (pair.getPairAddress().equals(address)) {
                return pair;
            }
        }
        throw new NotFoundException("Pair {} not found", address);
    }


    private void addBalance(String tokenAddress, BigInteger amount, long timestamp) {
        Balance balance = balances.get(tokenAddress);
        if (balance == null) {
            balance = new Balance(tokenAddress);
        }
        balance.addAmount(amount, timestamp);
        balances.put(tokenAddress, balance);
    }
}
