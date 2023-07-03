package com.natu.graalvm.domain.transaction.application;

import com.natu.graalvm.domain.transaction.core.model.Transaction;
import com.natu.graalvm.domain.transaction.core.model.TransactionResponse;
import com.natu.graalvm.domain.transaction.core.port.incomming.RetrieveTransaction;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final RetrieveTransaction retrieveTransaction;

    public TransactionController(RetrieveTransaction retrieveTransaction) {
        this.retrieveTransaction = retrieveTransaction;
    }

    @GetMapping("/{hash}")
    public TransactionResponse getTransaction(@PathVariable String hash) {
        Transaction transaction = retrieveTransaction.handle(hash);

        return new TransactionResponse(transaction);
    }

//    @PostMapping("/getProfit")
//    public void addLogToTransaction(@RequestBody ProfitRequest request) {
//        retrieveTransaction.handleRequest(request);
//    }
}
