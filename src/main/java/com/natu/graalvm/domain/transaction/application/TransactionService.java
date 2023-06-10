package com.natu.graalvm.domain.transaction.application;

import com.natu.graalvm.domain.transaction.core.model.AddTransactionCommand;
import com.natu.graalvm.domain.transaction.core.port.incomming.AddNewTransaction;
import org.springframework.stereotype.Service;


@Service
public class TransactionService {

    private final AddNewTransaction addNewTransaction;

    public TransactionService(AddNewTransaction addNewTransaction) {
        this.addNewTransaction = addNewTransaction;
    }

    public void addNewTransaction(AddTransactionCommand command) {
        addNewTransaction.handle(command);
    }

}
