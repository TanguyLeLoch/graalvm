package com.natu.graalvm.domain.transaction.core.port;

import com.natu.graalvm.domain.transaction.core.model.AddTransactionCommand;
import com.natu.graalvm.domain.transaction.core.model.Transaction;
import com.natu.graalvm.domain.transaction.core.port.incomming.AddNewTransaction;
import com.natu.graalvm.domain.transaction.core.port.incomming.RetrieveTransaction;
import com.natu.graalvm.domain.transaction.core.port.outgoing.TransactionDatabase;
import com.natu.graalvm.presentation.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Slf4j
public class TransactionFacade implements AddNewTransaction, RetrieveTransaction {
    private final TransactionDatabase database;
    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionFacade.class);

    public TransactionFacade(TransactionDatabase database) {
        this.database = database;
    }

    @Override
    public void handle(AddTransactionCommand command) {
        Transaction transaction = new Transaction(command.getHash());
        Transaction savedTx = database.save(transaction);
        LOGGER.info("Transaction saved: {}", savedTx.getHash());
    }

    @Override
    public Transaction handle(String hash) {
        Transaction transaction = database.findByHash(hash).orElseThrow(
                () -> new NotFoundException("Transaction {0} not found", hash));
        LOGGER.info("Transaction retrieved: {}", hash);
        return transaction;
    }
}
