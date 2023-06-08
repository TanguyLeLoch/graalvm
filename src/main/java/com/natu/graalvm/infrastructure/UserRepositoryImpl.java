package com.natu.graalvm.infrastructure;

import com.natu.graalvm.domain.User;
import org.bson.Document;
import org.springframework.data.mongodb.core.MongoTemplate;
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
    public Optional<User> findById(String id) {
        Document document = mongoTemplate.findById(id, Document.class, "user");
        if (document == null) {
            return Optional.empty();
        }
        return Optional.of(new User(document.getString("_id"), document.getString("name")));
    }
}
