package com.natu.graalvm.infrastructure;

import com.natu.graalvm.domain.User;
import org.springframework.stereotype.Component;


@Component
public class UserRepositoryImpl implements UserRepository {

    private final UserRepositorySpringData userRepositorySpringData;

    public UserRepositoryImpl(UserRepositorySpringData userRepositorySpringData) {
        this.userRepositorySpringData = userRepositorySpringData;
    }

    public User createUser(User user) {
        UserInfra userInfra = new UserInfra(user);
        UserInfra userCreated = userRepositorySpringData.save(userInfra);
        return userCreated.toDomain();
    }
}
