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
        Document userToSave = new Document();
        userToSave.put("_id", user.getId());
        userToSave.put("name", user.getName());
        Document saved = mongoTemplate.insert(userToSave, "user");
        String id = saved.getObjectId("_id").toString();
        return new User(id, saved.getString("name"));
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
