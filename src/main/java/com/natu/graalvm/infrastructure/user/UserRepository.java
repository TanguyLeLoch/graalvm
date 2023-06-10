package com.natu.graalvm.infrastructure.user;

import com.natu.graalvm.domain.User;

import java.util.Optional;


public interface UserRepository {

    User insert(User user);

    Optional<User> findByAddress(String address);
}
