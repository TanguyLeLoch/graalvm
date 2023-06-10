package com.natu.graalvm.application.transaction;

import com.natu.graalvm.domain.Transaction;
import com.natu.graalvm.infrastructure.transaction.TransactionRepository;
import com.natu.graalvm.presentation.NotFoundException;
import org.springframework.stereotype.Service;


@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;

    TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public TransactionResource createTransaction(String hash) {

        Transaction transaction = new Transaction(hash);
        Transaction transactionCreated = transactionRepository.insert(transaction);
        return new TransactionResource(transactionCreated);
    }

    public TransactionResource getTransaction(String hash) {
        Transaction transaction = transactionRepository.findByHash(hash)
                .orElseThrow(() -> new NotFoundException("Transaction {0} not found", hash));
        return new TransactionResource(transaction);
    }
}
