package com.natu.graalvm.domain.event.core.port.incomming;

import com.natu.graalvm.domain.event.core.model.Event;

import java.util.List;

public interface RetrieveEvent {
    List<Event> handleUnprocessedEvent(String name);
}
