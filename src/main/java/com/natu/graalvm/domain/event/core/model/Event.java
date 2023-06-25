package com.natu.graalvm.domain.event.core.model;

import lombok.Getter;

@Getter
public class Event {

    private final String id;
    private final String name;
    private final Object payload;
    private final long timestamp;
    private String status;

    public Event(String name, Object payload) {
        this.id = null;
        this.name = name;
        this.payload = payload;
        this.timestamp = System.currentTimeMillis();
        this.status = "CREATED";
    }

    public Event(String id, String name, Object payload, long timestamp, String status) {
        this.id = id;
        this.name = name;
        this.payload = payload;
        this.timestamp = timestamp;
        this.status = status;
    }

    public void processEvent() {
        if (!this.status.equals("CREATED")) {
            throw new IllegalStateException("Event is not in CREATED state");
        }
        this.status = "PROCESSING";
    }

    public void finishProcessing() {
        if (!this.status.equals("PROCESSING")) {
            throw new IllegalStateException("Event is not in PROCESSING state");
        }
        this.status = "PROCESSED";
    }
}
