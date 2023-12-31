package com.natu.graalvm.domain.user.core.port.outgoing;

import com.natu.graalvm.domain.user.core.model.User;

import java.util.List;
import java.util.Optional;

public interface UserDatabase {
    User insert(User user);

    User addPair(User user, String pairAddress);

    User update(User user);

    Optional<User> findByAddress(String address);

    User removePair(User user, String pairAddress);

    User addPairs(User user, List<String> pairsToAdd);
}
