package com.natu.graalvm.domain.transaction.core.port;


import com.natu.graalvm.domain.common.exception.NotFoundException;
import com.natu.graalvm.domain.transaction.core.model.AddTransactionCommand;
import com.natu.graalvm.domain.transaction.core.model.Deduplicator;
import com.natu.graalvm.domain.transaction.core.model.Transaction;
import com.natu.graalvm.domain.transaction.core.port.incomming.AddNewTransaction;
import com.natu.graalvm.domain.transaction.core.port.incomming.RetrieveTransaction;
import com.natu.graalvm.domain.transaction.core.port.outgoing.Blockchain;
import com.natu.graalvm.domain.transaction.core.port.outgoing.TransactionDatabase;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;

import java.util.List;

@Slf4j
public class TransactionFacade implements AddNewTransaction, RetrieveTransaction {
    private final TransactionDatabase database;
    private final Blockchain blockchain;

    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionFacade.class);

    public TransactionFacade(TransactionDatabase database, Blockchain blockchain) {
        this.database = database;
        this.blockchain = blockchain;
    }

    @Override
    public void fromCommand(AddTransactionCommand command) {
        Transaction transaction = new Transaction(command.getHash(), command.getFrom(), command.getTo(), command.getBlockNumber());
        Transaction savedTx = database.save(transaction);
        LOGGER.info("Transaction saved: {}", savedTx.getHash());
    }

    @Override
    public void newFromBlockchain(String address) {
        List<Transaction> existingTransactions = database.findByUserAddress(address);
        long startBlock = existingTransactions.size() > 0 ? existingTransactions.get(0).getBlockNumber() : 0;
        long endBlock = 99999999;
        List<Transaction> candidates = blockchain.getTransactions(address, startBlock, endBlock);
        List<Transaction> toSave = Deduplicator.deduplicate(existingTransactions, candidates);
        for (Transaction transaction : toSave) {
            saveTx(transaction);
        }
        LOGGER.info("{} new transaction(s) saved", toSave.size());
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
}
