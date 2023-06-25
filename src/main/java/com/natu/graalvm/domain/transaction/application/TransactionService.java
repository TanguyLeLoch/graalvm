package com.natu.graalvm.domain.transaction.application;

import com.natu.graalvm.domain.transaction.core.model.Transaction;
import com.natu.graalvm.domain.transaction.core.port.incomming.AddNewTransaction;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class TransactionService {

    private final AddNewTransaction addNewTransaction;

    public TransactionService(AddNewTransaction addNewTransaction) {
        this.addNewTransaction = addNewTransaction;
    }

    public void addNewTransactionFromBlockchain(String address) {
        addNewTransaction.fromBlockchain(address);
    }

    public void ingestTransactionsFromCsv(List<Transaction> transactions) {
        addNewTransaction.fromCsvRecord(transactions);
    }

    public void retrieveTxFromUsers(List<String> address) {
        for (String addr : address) {
            addNewTransactionFromBlockchain(addr);
        }
    }
}
