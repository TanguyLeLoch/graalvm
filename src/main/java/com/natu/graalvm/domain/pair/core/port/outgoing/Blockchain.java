package com.natu.graalvm.domain.pair.core.port.outgoing;

import com.natu.graalvm.domain.pair.core.model.Pair;

import java.util.Optional;

public interface Blockchain {

    Optional<Pair> getPair(String address);

    void getTxLog(String address);
}
