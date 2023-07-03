package com.natu.graalvm.domain.user.core.port;

import com.natu.graalvm.domain.common.exception.NotFoundException;
import com.natu.graalvm.domain.pair.core.model.Pair;
import com.natu.graalvm.domain.user.core.model.User;
import com.natu.graalvm.domain.user.core.port.incomming.AddNewUser;
import com.natu.graalvm.domain.user.core.port.incomming.AlterUser;
import com.natu.graalvm.domain.user.core.port.incomming.RetrieveUser;
import com.natu.graalvm.domain.user.core.port.outgoing.UserDatabase;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
public class UserFacade implements AddNewUser, RetrieveUser, AlterUser {
    private final UserDatabase database;
    private static final Logger LOGGER = LoggerFactory.getLogger(UserFacade.class);

    public UserFacade(UserDatabase database) {
        this.database = database;
    }

    @Override
    public User handleGetOrCreate(String address) {
        return database.findByAddress(address).orElseGet(() -> createNewUser(address));
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
    public User addPair(String userAddress, String pairAddress) {
        User user = database.findByAddress(userAddress).orElseThrow(
                () -> new NotFoundException("User {0} not found", userAddress));
        for (Pair pair : user.getPairs()) {
            if (pair.getPairAddress().equals(pairAddress)) {
                LOGGER.info("Pair already added to user: {}", userAddress);
                return user;
            }
        }
        User userSaved = database.addPair(user, pairAddress);
        LOGGER.info("Pair added to user: {}", userAddress);
        return userSaved;
    }

    @Override
    public User addPairs(String userAddress, Set<String> pairs) {
        User user = database.findByAddress(userAddress).orElseThrow(
                () -> new NotFoundException("User {0} not found", userAddress));
        List<String> pairsToAdd = new ArrayList<>();
        for (String pairAddress : pairs) {
            Set<String> userPairs = user.getPairs().stream()
                    .map(Pair::getPairAddress)
                    .collect(Collectors.toSet());

            if (!userPairs.contains(pairAddress)) {
                pairsToAdd.add(pairAddress);
            }
        }
        User userSaved = database.addPairs(user, pairsToAdd);
        LOGGER.info("Pair added to user: {}", userAddress);
        return userSaved;
    }

    @Override
    public User removePair(String userAddress, String pairAddress) {
        User user = database.findByAddress(userAddress).orElseThrow(
                () -> new NotFoundException("User {0} not found", userAddress));
        User userSaved = database.removePair(user, pairAddress);
        LOGGER.info("Pair removed from user: {}", userAddress);
        return userSaved;
    }

    @Override
    public User update(User user) {
        return database.update(user);
    }

}
