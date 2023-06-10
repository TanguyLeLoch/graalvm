package com.natu.graalvm.infrastructure;

import com.natu.graalvm.domain.User;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserRepositoryImpl implements UserRepository {

    private final MongoTemplate mongoTemplate;

    UserRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public User insert(User user) {
        UserInfra userInfra = new UserInfra(user);
        UserInfra saved = mongoTemplate.insert(userInfra);
        return saved.toDomain();
    }

    @Override
    public Optional<User> findByAddress(String address) {
        Query query = Query.query(Criteria.where("_id").is(address));
        UserInfra userInfra = mongoTemplate.findOne(query, UserInfra.class);
        if (userInfra != null) {
            return Optional.of(userInfra.toDomain());
        } else {
            return Optional.empty();
        }
    }
}
