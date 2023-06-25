package com.natu.graalvm.domain.transaction.core.model;

import com.natu.graalvm.domain.transaction.core.model.Transaction.ContractCall;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "transactions")
public class TransactionInfraMongo {
    @Id
    String hash;
    String from;
    String to;
    Long blockNumber;
    String status;
    ContractCall contractCall;
    List<Log> logs;
    String index;


    public TransactionInfraMongo(Transaction transaction) {
        this.hash = transaction.getHash();
        this.from = transaction.getFrom();
        this.to = transaction.getTo();
        this.blockNumber = transaction.getBlockNumber();
        this.status = transaction.getStatus().name();
        this.contractCall = transaction.getContractCall();
        this.logs = transaction.getLogs();
        this.index = transaction.getIndex();
    }

    @PersistenceCreator
    public TransactionInfraMongo(String hash, String from, String to, Long blockNumber, String status, ContractCall contractCall, List<Log> logs, String index) {
        this.hash = hash;
        this.from = from;
        this.to = to;
        this.blockNumber = blockNumber;
        this.status = status;
        this.contractCall = contractCall;
        this.logs = logs;
        this.index = index;
    }

    public Transaction toDomain() {
        return new Transaction(hash, from, to, blockNumber,
                Transaction.Status.valueOf(status), contractCall, logs, index);
    }
}
