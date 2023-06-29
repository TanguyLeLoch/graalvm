package com.natu.graalvm.domain.pair.infrastructure;


import com.natu.graalvm.domain.common.exception.FunctionalException;
import com.natu.graalvm.domain.pair.core.model.Pair;
import com.natu.graalvm.domain.pair.core.model.PairInfraMongo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Slf4j
public class PairRepository {

    private final MongoTemplate mongoTemplate;

    PairRepository(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public Pair insert(Pair pair) {
        PairInfraMongo pairInfraMongo = new PairInfraMongo(pair);
        try {
            PairInfraMongo saved = mongoTemplate.insert(pairInfraMongo);
            return saved.toDomain();
        } catch (DuplicateKeyException e) {
            log.info("Pair already exists", e);
            throw new FunctionalException("Pair already exists");
        } catch (Exception e) {
            log.error("Error while inserting pair", e);
        }
        throw new FunctionalException("Error while inserting pair");
    }

    public Optional<Pair> findByAddress(String address) {
        PairInfraMongo pairInfraMongo = mongoTemplate.findById(address, PairInfraMongo.class);
        if (pairInfraMongo != null) {
            return Optional.of(pairInfraMongo.toDomain());
        } else {
            return Optional.empty();
        }
    }

}
