package com.natu.graalvm.domain.event.core.port;

import com.natu.graalvm.domain.event.core.model.Event;
import com.natu.graalvm.domain.event.core.port.incomming.RetrieveEvent;
import com.natu.graalvm.domain.event.core.port.outgoing.ProcessEvent;
import com.natu.graalvm.domain.transaction.core.port.outgoing.EventDatabase;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class EventFacade implements RetrieveEvent, ProcessEvent {

    EventDatabase eventDatabase;

    public EventFacade(EventDatabase eventDatabase) {
        this.eventDatabase = eventDatabase;
    }

    @Override
    public List<Event> handleUnprocessedEvent(String name) {
        List<Event> events = eventDatabase.getUnprocessedEvent(name);
        log.info("Retrieved {} unprocessed events", events.size());
        return events;
    }

    @Override
    public void markEventAsProcessing(Event event) {
        event.processEvent();
        eventDatabase.save(event);
    }


    @Override
    public void markEventAsProcessed(Event event) {
        event.finishProcessing();
        eventDatabase.save(event);
    }
}
