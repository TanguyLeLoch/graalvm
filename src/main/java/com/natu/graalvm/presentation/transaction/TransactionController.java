package com.natu.graalvm.presentation.transaction;

import com.natu.graalvm.application.transaction.TransactionResource;
import com.natu.graalvm.application.transaction.TransactionService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("/{hash}")
    public TransactionResponse getTransaction(@PathVariable String hash) {
        TransactionResource transactionResource = transactionService.getTransaction(hash);
        return new TransactionResponse(transactionResource);
    }

    @PostMapping()
    public TransactionResponse createTransaction(@RequestParam String hash) {
        TransactionResource transactionResource = transactionService.createTransaction(hash);
        return new TransactionResponse(transactionResource);
    }
}
