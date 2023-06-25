package com.natu.graalvm.domain.transaction.core.port.outgoing;

import com.natu.graalvm.domain.event.core.model.Event;

import java.util.List;

public interface EventDatabase {
    Event save(Event event);


    List<Event> getUnprocessedEvent(String name);
}
