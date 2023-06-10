package com.natu.graalvm.domain.user.infrastructure;

import com.natu.graalvm.domain.user.core.model.User;
import com.natu.graalvm.domain.user.core.port.outgoing.UserDatabase;

import java.util.Optional;

public class UserDatabaseAdapter implements UserDatabase {

    private final UserRepository repository;

    public UserDatabaseAdapter(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public User save(User user) {
        return repository.insert(user);
    }

    @Override
    public Optional<User> findByAddress(String address) {
        return repository.findByAddress(address);
    }
}
