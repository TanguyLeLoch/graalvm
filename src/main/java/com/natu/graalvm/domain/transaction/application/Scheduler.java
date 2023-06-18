package com.natu.graalvm.domain.transaction.application;

import org.springframework.scheduling.annotation.Scheduled;


public class Scheduler {

    private final TransactionService transactionService;

    public Scheduler(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @Scheduled(fixedRate = 100)
    public void scheduleFixed() {
        // get new transactions from you got a fast car
        String youGotAFastCar = "0x1961ed2784d438fbc6939df0b33ef661b7b2994d";
        transactionService.addNewTransactionFromBlockchain(youGotAFastCar);
    }
}
