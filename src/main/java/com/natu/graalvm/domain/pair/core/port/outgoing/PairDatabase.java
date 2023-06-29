package com.natu.graalvm.domain.pair.core.port.outgoing;

import com.natu.graalvm.domain.pair.core.model.Pair;

import java.util.Optional;

public interface PairDatabase {
    Pair insert(Pair pair);

    Optional<Pair> findByAddress(String address);
}
