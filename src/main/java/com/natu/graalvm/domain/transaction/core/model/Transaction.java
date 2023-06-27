package com.natu.graalvm.domain.transaction.core.model;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Transaction {
    private final String hash;
    private final String from;
    private final String to;
    private final long blockNumber;
    private Status status;
    private final ContractCall contractCall;
    private final List<Log> logs;
    private final String index;
    private final long timestamp;

    public Transaction(String hash, String from, String to, long blockNumber, Status status, ContractCall contractCall, List<Log> logs, String index, long timestamp) {
        this.hash = hash;
        this.from = from;
        this.to = to;
        this.blockNumber = blockNumber;
        this.status = status;
        this.contractCall = contractCall;
        this.logs = logs;
        this.index = index;
        this.timestamp = timestamp;

    }

    public Transaction(String hash, String from, String to, long blockNumber, String index, long timestamp) {
        this.hash = hash;
        this.from = from;
        this.to = to;
        this.blockNumber = blockNumber;
        this.status = Status.INITIATED;
        this.contractCall = null;
        this.logs = new ArrayList<>();
        this.index = index;
        this.timestamp = timestamp;
    }

    public void addLog(Log log) {
        this.logs.add(log);
        this.status = Status.LOG_ADDED;
    }

    public void fetch() {
        this.status = Status.FETCHED;
    }

    public enum Status {
        INITIATED,
        LOG_ADDED,
        FETCHED
    }

    static class ContractCall {
        private final String method;

        public ContractCall(String method) {
            this.method = method;
        }
    }
}
