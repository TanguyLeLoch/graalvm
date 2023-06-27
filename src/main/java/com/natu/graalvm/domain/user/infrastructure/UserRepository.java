package com.natu.graalvm.domain.user.infrastructure;

import com.natu.graalvm.domain.common.exception.FunctionalException;
import com.natu.graalvm.domain.user.core.model.User;
import com.natu.graalvm.domain.user.core.model.UserInfraMongo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Slf4j
public class UserRepository {

    private final MongoTemplate mongoTemplate;

    UserRepository(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public User insert(User user) {
        UserInfraMongo userInfraMongo = new UserInfraMongo(user);
        try {
            UserInfraMongo saved = mongoTemplate.insert(userInfraMongo);
            return saved.toDomain();
        } catch (DuplicateKeyException e) {
            log.info("User already exists", e);
            throw new FunctionalException("User already exists");
        } catch (Exception e) {
            log.error("Error while inserting user", e);
        }
        throw new FunctionalException("Error while inserting user");
    }

    public Optional<User> findByAddress(String address) {
        UserInfraMongo userInfraMongo = mongoTemplate.findById(address, UserInfraMongo.class);
        if (userInfraMongo != null) {
            return Optional.of(userInfraMongo.toDomain());
        } else {
            return Optional.empty();
        }
    }

    public User update(User user) {
        UserInfraMongo userInfraMongo = new UserInfraMongo(user);
        UserInfraMongo saved = mongoTemplate.save(userInfraMongo);
        return saved.toDomain();
    }
}
