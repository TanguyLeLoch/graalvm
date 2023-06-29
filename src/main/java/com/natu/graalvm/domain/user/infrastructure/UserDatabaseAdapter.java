package com.natu.graalvm.domain.user.infrastructure;

import com.natu.graalvm.domain.user.core.model.User;
import com.natu.graalvm.domain.user.core.model.UserInfraMongo;
import com.natu.graalvm.domain.user.core.port.outgoing.RetrievePair;
import com.natu.graalvm.domain.user.core.port.outgoing.UserDatabase;

import java.util.Optional;

public class UserDatabaseAdapter implements UserDatabase {

    private final UserRepository repository;

    private final RetrievePair retrievePair;

    public UserDatabaseAdapter(UserRepository repository, RetrievePair retrievePair) {
        this.repository = repository;
        this.retrievePair = retrievePair;
    }

    @Override
    public Optional<User> findByAddress(String address) {
        Optional<UserInfraMongo> userInfraMongo = repository.findByAddress(address);
        return userInfraMongo.map(infraMongo -> infraMongo.toDomain(retrievePair));
    }

    @Override
    public User removePair(User user, String pairAddress) {
        UserInfraMongo userInfraMongo = new UserInfraMongo(user);
        userInfraMongo.removePair(pairAddress);
        UserInfraMongo userInfraMongoSaved = repository.update(userInfraMongo);
        return userInfraMongoSaved.toDomain(retrievePair);
    }

    @Override
    public User insert(User user) {
        UserInfraMongo userInfraMongo = new UserInfraMongo(user);
        UserInfraMongo saved = repository.insert(userInfraMongo);
        return saved.toDomain(retrievePair);
    }

    @Override
    public User addPair(User user, String pairAddress) {
        UserInfraMongo userInfraMongo = new UserInfraMongo(user);
        userInfraMongo.addPair(pairAddress);
        UserInfraMongo saved = repository.update(userInfraMongo);
        return saved.toDomain(retrievePair);
    }

    @Override
    public User update(User user) {
        UserInfraMongo userInfraMongo = new UserInfraMongo(user);
        UserInfraMongo saved = repository.update(userInfraMongo);
        return saved.toDomain(retrievePair);
    }

}
