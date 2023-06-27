package com.natu.graalvm.domain.transaction.core.port;


import com.natu.graalvm.domain.common.exception.NotFoundException;
import com.natu.graalvm.domain.event.core.model.Event;
import com.natu.graalvm.domain.transaction.application.ProfitRequest;
import com.natu.graalvm.domain.transaction.core.model.Deduplicator;
import com.natu.graalvm.domain.transaction.core.model.Log;
import com.natu.graalvm.domain.transaction.core.model.Transaction;
import com.natu.graalvm.domain.transaction.core.port.incomming.AddNewTransaction;
import com.natu.graalvm.domain.transaction.core.port.incomming.RetrieveTransaction;
import com.natu.graalvm.domain.transaction.core.port.outgoing.Blockchain;
import com.natu.graalvm.domain.transaction.core.port.outgoing.EventDatabase;
import com.natu.graalvm.domain.transaction.core.port.outgoing.TransactionDatabase;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class TransactionFacade implements AddNewTransaction, RetrieveTransaction {
    private final TransactionDatabase database;
    private final EventDatabase eventDatabase;
    private final Blockchain blockchain;
    private final static String SWAP_TOPIC = "0xd78ad95fa46c994b6551d0da85fc275fe613ce37657fb8d5e3d130840159d822";

    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionFacade.class);

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
        LOGGER.info("{} transaction(s) saved", transactions.size());
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
        LOGGER.info("{} transaction(s) saved", toSave.size());
    }

    private void saveTx(Transaction transaction) {
        try {
            database.save(transaction);
        } catch (DuplicateKeyException e) {
            LOGGER.debug("Transaction {} already exists in database", transaction.getHash());
        } catch (Exception e) {
            LOGGER.error("Error while saving transaction {}", transaction.getHash(), e);
        }
    }

    @Override
    public Transaction handle(String hash) {
        Transaction transaction = database.findByHash(hash).orElseThrow(
                () -> new NotFoundException("Transaction {0} not found", hash));
        LOGGER.info("Transaction retrieved: {}", hash);
        return transaction;
    }

    @Override
    public List<Transaction> handleRequest(ProfitRequest request) {
        List<Transaction> txs = blockchain.getTransactions(request.address());
        List<Log> logs = blockchain.getTransactionLogs(request.pair(), List.of(SWAP_TOPIC));
        Map<String, List<Log>> logGroupedByHash = new HashMap<>();
        for (Log log : logs) {
            logGroupedByHash.computeIfAbsent(log.getTransactionHash(), k -> new ArrayList<>()).add(log);
        }
        List<Transaction> swapTxs = new ArrayList<>();
        for (Transaction tx : txs) {
            List<Log> logByHash = logGroupedByHash.get(tx.getHash());
            if (logByHash != null) {
                for (Log log : logByHash) {
                    LOGGER.info("Transaction {} is a swap", tx.getHash());
                    tx.addLog(log);
                    swapTxs.add(tx);
                }
            }
        }
        for (Transaction tx : txs) {
            saveTx(tx);
        }
        return swapTxs;
    }
}
