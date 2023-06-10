package com.natu.graalvm.application;

import com.natu.graalvm.domain.User;
import com.natu.graalvm.infrastructure.UserRepository;
import com.natu.graalvm.presentation.NotFoundException;
import org.springframework.stereotype.Service;


@Service
public class UserService {

    private final UserRepository userRepository;

    UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserResource createUser(String address) {

        User user = new User(address);
        User userCreated = userRepository.insert(user);
        return new UserResource(userCreated);
    }

    public UserResource getUser(String address) {
        User user = userRepository.findByAddress(address)
                .orElseThrow(() -> new NotFoundException("User {0} not found", address));
        return new UserResource(user);
    }
}
