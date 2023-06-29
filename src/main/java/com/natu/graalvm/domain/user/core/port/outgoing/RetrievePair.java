package com.natu.graalvm.domain.user.core.port.outgoing;

import com.natu.graalvm.domain.pair.core.model.Pair;

import java.util.Optional;

public interface RetrievePair {

    Optional<Pair> findByAddress(String address);
}
