package com.natu.graalvm.domain.event.infrastructure;

import com.natu.graalvm.domain.event.core.model.Event;
import com.natu.graalvm.domain.event.core.model.EventInfraMongo;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

public class EventRepository {

    private final MongoTemplate mongoTemplate;

    public EventRepository(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public Event insert(Event event) {
        EventInfraMongo eventInfraMongo = new EventInfraMongo(event);
        EventInfraMongo saved;
        if (eventInfraMongo.getId() == null) {
            saved = mongoTemplate.insert(eventInfraMongo);
        } else {
            saved = mongoTemplate.save(eventInfraMongo);
        }
        return saved.toDomain();
    }

    public List<Event> findByNameAndStatus(String name, List<String> status) {
        List<EventInfraMongo> eventInfraMongos = mongoTemplate.find(query(
                        where("name").regex("^" + name + ".*").and("status").in(status)),
                EventInfraMongo.class
        );
        return eventInfraMongos.stream().map(EventInfraMongo::toDomain).toList();
    }
}
