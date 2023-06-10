package com.natu.graalvm.domain.user.core.port;

import com.natu.graalvm.domain.common.exception.NotFoundException;
import com.natu.graalvm.domain.user.core.model.AddUserCommand;
import com.natu.graalvm.domain.user.core.model.User;
import com.natu.graalvm.domain.user.core.port.incomming.AddNewUser;
import com.natu.graalvm.domain.user.core.port.incomming.RetrieveUser;
import com.natu.graalvm.domain.user.core.port.outgoing.UserDatabase;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Slf4j
public class UserFacade implements AddNewUser, RetrieveUser {
    private final UserDatabase database;
    private static final Logger LOGGER = LoggerFactory.getLogger(UserFacade.class);

    public UserFacade(UserDatabase database) {
        this.database = database;
    }

    @Override
    public void handle(AddUserCommand command) {
        User user = new User(command.getAddress());
        User savedTx = database.save(user);
        LOGGER.info("User saved: {}", savedTx.getAddress());
    }

    @Override
    public User handle(String address) {
        User user = database.findByAddress(address).orElseThrow(
                () -> new NotFoundException("User {0} not found", address));
        LOGGER.info("User retrieved: {}", address);
        return user;
    }
}
