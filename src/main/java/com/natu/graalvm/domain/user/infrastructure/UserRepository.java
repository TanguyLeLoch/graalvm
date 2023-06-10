package com.natu.graalvm.domain.user.infrastructure;

import com.natu.graalvm.domain.user.core.model.User;
import com.natu.graalvm.domain.user.core.model.UserInfraMongo;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserRepository {

    private final MongoTemplate mongoTemplate;

    UserRepository(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public User insert(User user) {
        UserInfraMongo userInfraMongo = new UserInfraMongo(user);
        UserInfraMongo saved = mongoTemplate.insert(userInfraMongo);
        return saved.toDomain();
    }

    public Optional<User> findByAddress(String address) {
        UserInfraMongo userInfraMongo = mongoTemplate.findById(address, UserInfraMongo.class);
        if (userInfraMongo != null) {
            return Optional.of(userInfraMongo.toDomain());
        } else {
            return Optional.empty();
        }
    }
}
