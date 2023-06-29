package com.natu.graalvm.domain.user.infrastructure;

import com.natu.graalvm.domain.common.exception.FunctionalException;
import com.natu.graalvm.domain.user.core.model.UserInfraMongo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.Optional;

@Slf4j
public class UserRepository {

    private final MongoTemplate mongoTemplate;

    public UserRepository(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public UserInfraMongo insert(UserInfraMongo userInfraMongo) {
        try {
            return mongoTemplate.insert(userInfraMongo);
        } catch (DuplicateKeyException e) {
            log.info("User already exists", e);
            throw new FunctionalException("User already exists");
        } catch (Exception e) {
            log.error("Error while inserting user", e);
            throw e;
        }
//        throw new FunctionalException("Error while inserting user");
    }

    public Optional<UserInfraMongo> findByAddress(String address) {
        UserInfraMongo userInfraMongo = mongoTemplate.findById(address, UserInfraMongo.class);
        return Optional.ofNullable(userInfraMongo);
    }

    public UserInfraMongo update(UserInfraMongo userInfraMongo) {
        return mongoTemplate.save(userInfraMongo);
    }
}
