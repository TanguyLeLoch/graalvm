package com.natu.graalvm.infrastructure;

import com.natu.graalvm.domain.User;

public interface UserRepository {
    User createUser(User user);
}
