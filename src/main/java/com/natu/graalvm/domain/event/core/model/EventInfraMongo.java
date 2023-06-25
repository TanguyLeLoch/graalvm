package com.natu.graalvm.domain.event.core.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "event")

public class EventInfraMongo {

    @Id
    private String id;
    private final String name;
    private final Object payload;
    private final long timestamp;
    private final String status;


    public EventInfraMongo(Event event) {
        this.id = event.getId();
        this.name = event.getName();
        this.payload = event.getPayload();
        this.timestamp = event.getTimestamp();
        this.status = event.getStatus();
    }

    @PersistenceCreator
    public EventInfraMongo(String id, String name, Object payload, long timestamp, String status) {
        this.id = id;
        this.name = name;
        this.payload = payload;
        this.timestamp = timestamp;
        this.status = status;
    }

    public Event toDomain() {
        return new Event(id, name, payload, timestamp, status);
    }

    public String getId() {
        return id;
    }
}
