package com.natu.graalvm.domain.user.core.port.outgoing;

import com.natu.graalvm.domain.user.core.model.User;

import java.util.Optional;

public interface UserDatabase {
    User insert(User user);

    User update(User user);

    Optional<User> findByAddress(String address);
}
