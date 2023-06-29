package com.natu.graalvm.domain.pair.infrastructure;

import com.natu.graalvm.domain.pair.core.model.Pair;
import com.natu.graalvm.domain.pair.core.port.outgoing.PairDatabase;

import java.util.Optional;

public class PairDatabaseAdapter implements PairDatabase {

    private final PairRepository repository;

    public PairDatabaseAdapter(PairRepository repository) {
        this.repository = repository;
    }

    @Override
    public Pair insert(Pair pair) {
        return repository.insert(pair);
    }

    @Override
    public Optional<Pair> findByAddress(String address) {
        return repository.findByAddress(address);
    }
}
