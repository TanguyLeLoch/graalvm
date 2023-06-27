package com.natu.graalvm.domain.user.core.port;

import com.natu.graalvm.domain.common.exception.NotFoundException;
import com.natu.graalvm.domain.user.core.model.AddPairCommand;
import com.natu.graalvm.domain.user.core.model.User;
import com.natu.graalvm.domain.user.core.port.incomming.AddNewUser;
import com.natu.graalvm.domain.user.core.port.incomming.AlterUser;
import com.natu.graalvm.domain.user.core.port.incomming.RetrieveUser;
import com.natu.graalvm.domain.user.core.port.outgoing.UserDatabase;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Slf4j
public class UserFacade implements AddNewUser, RetrieveUser, AlterUser {
    private final UserDatabase database;
    private static final Logger LOGGER = LoggerFactory.getLogger(UserFacade.class);

    public UserFacade(UserDatabase database) {
        this.database = database;
    }

    @Override
    public User handleGetOrCreate(String address) {
        return database.findByAddress(address).orElse(
                createNewUser(address));
    }

    private User createNewUser(String address) {
        User user = new User(address);
        User savedUser = database.insert(user);
        LOGGER.info("User saved: {}", savedUser.getAddress());
        return savedUser;
    }

    @Override
    public User handle(String address) {
        User user = database.findByAddress(address).orElseThrow(
                () -> new NotFoundException("User {0} not found", address));
        LOGGER.info("User retrieved: {}", address);
        return user;
    }

    @Override
    public void addPair(String userAddress, AddPairCommand command) {
        User user = database.findByAddress(userAddress).orElseThrow(
                () -> new NotFoundException("User {0} not found", userAddress));
        user.addPair(command.toPair());
        database.update(user);
        LOGGER.info("Pair added to user: {}", userAddress);
    }

    @Override
    public User update(User user) {
        return database.update(user);
    }
}
