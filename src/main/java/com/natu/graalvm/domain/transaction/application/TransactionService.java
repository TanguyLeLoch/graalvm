package com.natu.graalvm.domain.transaction.application;

import com.natu.graalvm.domain.pair.core.model.Pair;
import com.natu.graalvm.domain.transaction.core.model.Transaction;
import com.natu.graalvm.domain.transaction.core.port.incomming.AddNewTransaction;
import com.natu.graalvm.domain.transaction.core.port.incomming.RetrieveTransaction;
import com.natu.graalvm.domain.user.core.model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class TransactionService {

    private final AddNewTransaction addNewTransaction;
    private final RetrieveTransaction retrieveTransaction;

    public TransactionService(AddNewTransaction addNewTransaction, RetrieveTransaction retrieveTransaction) {
        this.addNewTransaction = addNewTransaction;
        this.retrieveTransaction = retrieveTransaction;
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

    public List<Transaction> retrieveTxs(User user) {
        List<Transaction> swapTxs = new ArrayList<>();
        for (Pair pair : user.getPairs()) {
            swapTxs.addAll(retrieveTransaction.handleRequest(
                    new ProfitRequest(
                            user.getAddress(),
                            pair.getToken0().getAddress(),
                            pair.getToken1().getAddress(),
                            pair.getPairAddress())));
        }


        return swapTxs;
    }


}
