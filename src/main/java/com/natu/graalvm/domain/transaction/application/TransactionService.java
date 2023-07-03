package com.natu.graalvm.domain.transaction.application;

import com.natu.graalvm.domain.transaction.core.model.Log;
import com.natu.graalvm.domain.transaction.core.model.Transaction;
import com.natu.graalvm.domain.transaction.core.port.incomming.AddNewTransaction;
import com.natu.graalvm.domain.transaction.core.port.incomming.RetrieveTransaction;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


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

//    public List<Transaction> retrieveTxs(User user) {
//        List<Transaction> swapTxs = new ArrayList<>();
//        for (Pair pair : user.getPairs()) {
//            swapTxs.addAll(retrieveTransaction.handleRequest(
//                    new ProfitRequest(
//                            user.getAddress(),
//                            pair.getToken0().getAddress(),
//                            pair.getToken1().getAddress(),
//                            pair.getPairAddress())));
//        }
//
//
//        return swapTxs;
//    }


    public void addLogToUserTransactions(String userAddress) {
        addNewTransaction.addLogToUserTransactions(userAddress);
    }

    public Set<String> retrievePairs(String userAddress) {

        List<Transaction> txs = retrieveSwapTxs(userAddress);
        Set<String> pairs = new HashSet<>();
        for (Transaction tx : txs) {
            for (Log log : tx.getLogs()) {
                if (log.getSwap() != null) {
                    pairs.add(log.getAddress());
                }
            }
        }
        return pairs;
    }

    public List<Transaction> retrieveSwapTxs(String userAddress) {
        List<Transaction> txs = retrieveTransaction.ofUser(userAddress);
        return txs
                .stream()
                .filter(tx -> tx.getLogs().stream().anyMatch(log -> log.getSwap() != null))
                .toList();
    }
}
