package com.natu.graalvm.domain.transaction.core.model;

import java.util.ArrayList;
import java.util.List;

public class Deduplicator {

    public static List<Transaction> deduplicate(List<Transaction> existingTransactions, List<Transaction> candidateTransactions) {
        List<Transaction> toBeAdded = new ArrayList<>();
        List<String> existingHashes = existingTransactions.stream().map(Transaction::getHash).toList();
        for (Transaction candidate : candidateTransactions) {
            if (!existingHashes.contains(candidate.getHash())) {
                toBeAdded.add(candidate);
            }
        }
        return toBeAdded;
    }
}
