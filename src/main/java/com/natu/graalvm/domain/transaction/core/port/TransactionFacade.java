package com.natu.graalvm.domain.transaction.core.port;


import com.natu.graalvm.domain.common.exception.NotFoundException;
import com.natu.graalvm.domain.event.core.model.Event;
import com.natu.graalvm.domain.transaction.core.model.Deduplicator;
import com.natu.graalvm.domain.transaction.core.model.Log;
import com.natu.graalvm.domain.transaction.core.model.Transaction;
import com.natu.graalvm.domain.transaction.core.port.incomming.AddNewTransaction;
import com.natu.graalvm.domain.transaction.core.port.incomming.RetrieveTransaction;
import com.natu.graalvm.domain.transaction.core.port.outgoing.Blockchain;
import com.natu.graalvm.domain.transaction.core.port.outgoing.EventDatabase;
import com.natu.graalvm.domain.transaction.core.port.outgoing.TransactionDatabase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;

import java.util.List;

@Slf4j
public class TransactionFacade implements AddNewTransaction, RetrieveTransaction {
    private final TransactionDatabase database;
    private final EventDatabase eventDatabase;
    private final Blockchain blockchain;

    public TransactionFacade(TransactionDatabase database, Blockchain blockchain, EventDatabase eventDatabase) {
        this.database = database;
        this.blockchain = blockchain;
        this.eventDatabase = eventDatabase;
    }

    @Override
    public void fromCsvRecord(List<Transaction> transactions) {
        for (Transaction transaction : transactions) {
            saveTx(transaction);
        }
        log.info("{} transaction(s) saved", transactions.size());
        Event event = new Event("transactions.created", transactions.stream().map(Transaction::getFrom).toList());
        eventDatabase.save(event);
    }

    @Override
    public void fromBlockchain(String address) {
        List<Transaction> existingTransactions = database.findByUserAddress(address);
        List<Transaction> candidates = blockchain.getTransactions(address);
        List<Transaction> toSave = Deduplicator.deduplicate(existingTransactions, candidates);
        for (Transaction transaction : toSave) {
            saveTx(transaction);
        }
        log.info("{} transaction(s) saved", toSave.size());
    }

    @Override
    public void addLogToUserTransactions(String userAddress) {
        List<Transaction> existingTransactions = database.findByUserAddress(userAddress);
        int count = 0;
        for (Transaction transaction : existingTransactions) {
            List<Log> logs = blockchain.getTransactionLogs(transaction.getHash());
            for (Log log : logs) {
                transaction.addLog(log);
            }
            saveTx(transaction);
            count++;
            log.info("processed {} / {} transactions", count, existingTransactions.size());
        }
    }

    private void saveTx(Transaction transaction) {
        try {
            database.save(transaction);
        } catch (DuplicateKeyException e) {
            log.debug("Transaction {} already exists in database", transaction.getHash());
        } catch (Exception e) {
            log.error("Error while saving transaction {}", transaction.getHash(), e);
        }
    }

    @Override
    public Transaction handle(String hash) {
        Transaction transaction = database.findByHash(hash).orElseThrow(
                () -> new NotFoundException("Transaction {0} not found", hash));
        log.info("Transaction retrieved: {}", hash);
        return transaction;
    }

    @Override
    public List<Transaction> ofUser(String userAddress) {
        List<Transaction> transactions = database.findByUserAddress(userAddress);
        log.info("{} transaction(s) retrieved for user {}", transactions.size(), userAddress);
        return transactions;
    }

//    @Override
//    public List<Transaction> handleRequest(ProfitRequest request) {
//        List<Transaction> txs = blockchain.getTransactions(request.address());
//        List<Log> logs = blockchain.getTransactionLogs(request.pair(), List.of(SWAP_TOPIC));
//        Map<String, List<Log>> logGroupedByHash = new HashMap<>();
//        for (Log log : logs) {
//            logGroupedByHash.computeIfAbsent(log.getTransactionHash(), k -> new ArrayList<>()).add(log);
//        }
//        List<Transaction> swapTxs = new ArrayList<>();
//        for (Transaction tx : txs) {
//            List<Log> logByHash = logGroupedByHash.get(tx.getHash());
//            if (logByHash != null) {
//                for (Log log : logByHash) {
//                    log.info("Transaction {} is a swap", tx.getHash());
//                    tx.addLog(log);
//                    swapTxs.add(tx);
//                }
//            }
//        }
//        for (Transaction tx : txs) {
//            saveTx(tx);
//        }
//        return swapTxs;
//    }
}
