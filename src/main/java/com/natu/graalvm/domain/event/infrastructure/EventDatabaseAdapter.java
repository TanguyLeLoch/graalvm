package com.natu.graalvm.domain.event.infrastructure;

import com.natu.graalvm.domain.event.core.model.Event;
import com.natu.graalvm.domain.transaction.core.port.outgoing.EventDatabase;

import java.util.List;

public class EventDatabaseAdapter implements EventDatabase {

    EventRepository repository;

    public EventDatabaseAdapter(EventRepository repository) {
        this.repository = repository;
    }

    @Override
    public Event save(Event event) {
        return repository.insert(event);
    }

    @Override
    public List<Event> getUnprocessedEvent(String name) {
        return repository.findByNameAndStatus(name, List.of("CREATED"));
    }
}
