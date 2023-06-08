package com.natu.graalvm.application;

import com.natu.graalvm.domain.User;
import com.natu.graalvm.infrastructure.UserRepository;
import org.springframework.stereotype.Service;


@Service
public class UserService {

    private final UserRepository userRepository;

    UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserResource createUser(String name) {

        User user = new User(null, name);
        User userCreated = userRepository.createUser(user);
        return new UserResource(userCreated);
    }

    public UserResource getUser(Long id) {
        User user = userRepository.getUser(id);
        return new UserResource(user);
    }
}
