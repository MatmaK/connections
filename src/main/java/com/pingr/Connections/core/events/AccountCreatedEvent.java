package com.pingr.Connections.core.events;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.Map;

@JsonSerialize
public class AccountCreatedEvent extends AccountEvent {
    public AccountCreatedEvent() {
    }

    public AccountCreatedEvent(String eventType, Long accountId, Map<String, Object> payload) {
        super(eventType, accountId, payload);
    }
}
