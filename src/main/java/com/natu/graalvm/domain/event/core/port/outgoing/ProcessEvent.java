package com.natu.graalvm.domain.event.core.port.outgoing;

import com.natu.graalvm.domain.event.core.model.Event;

public interface ProcessEvent {

    void markEventAsProcessing(Event event);

    void markEventAsProcessed(Event event);
}
