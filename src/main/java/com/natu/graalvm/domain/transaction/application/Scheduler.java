package com.natu.graalvm.domain.transaction.application;

import com.natu.graalvm.domain.common.exception.NotFoundException;
import com.natu.graalvm.domain.event.application.EventService;
import com.natu.graalvm.domain.event.core.model.Event;

import java.util.List;


public class Scheduler {

    private final TransactionService transactionService;
    private final EventService eventService;

    public Scheduler(TransactionService transactionService, EventService eventService) {
        this.transactionService = transactionService;
        this.eventService = eventService;
    }

    //    @Scheduled(fixedRate = 10000)
    public void scheduleFixed() {
        // get new transactions from you got a fast car
        String youGotAFastCar = "0x1961ed2784d438fbc6939df0b33ef661b7b2994d";
//        transactionService.addNewTransactionFromBlockchain(youGotAFastCar);
    }

    //    @Scheduled(fixedRate = 10000)
    public void processEvents() {
        List<Event> events = eventService.getUnprocessedEvent("transaction");
        for (Event event : events) {
            eventService.markEventAsProcessing(event);
        }
        for (Event event : events) {
            if (event.getName().equals("transactions.created")) {
                transactionService.retrieveTxFromUsers((List<String>) event.getPayload());
            } else {
                throw new NotFoundException("event not found");
            }
            eventService.markEventAsProcessed(event);
        }
    }
}
