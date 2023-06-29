package com.natu.graalvm.domain.pair.core.port.incomming;

import com.natu.graalvm.domain.pair.core.model.Pair;

public interface AddNewPair {
    Pair handleGetOrCreate(String address);
}
