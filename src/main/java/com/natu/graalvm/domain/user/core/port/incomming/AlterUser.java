package com.natu.graalvm.domain.user.core.port.incomming;

import com.natu.graalvm.domain.user.core.model.User;

import java.util.Set;

public interface AlterUser {
    User addPair(String userAddress, String pairAddress);

    User addPairs(String userAddress, Set<String> pairs);

    User update(User user);

    User removePair(String userAddress, String pairAddress);

}
