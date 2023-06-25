package com.natu.graalvm.domain.event.application;

import com.natu.graalvm.domain.event.core.model.Event;
import com.natu.graalvm.domain.event.core.port.incomming.RetrieveEvent;
import com.natu.graalvm.domain.event.core.port.outgoing.ProcessEvent;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {

    RetrieveEvent retrieveEvent;
    ProcessEvent processEvent;

    public EventService(RetrieveEvent retrieveEvent, ProcessEvent processEvent) {
        this.retrieveEvent = retrieveEvent;
        this.processEvent = processEvent;
    }

    public List<Event> getUnprocessedEvent(String name) {
        return retrieveEvent.handleUnprocessedEvent(name);
    }

    public void markEventAsProcessing(Event event) {
        processEvent.markEventAsProcessing(event);
    }

    public void markEventAsProcessed(Event event) {
        processEvent.markEventAsProcessed(event);
    }


}

