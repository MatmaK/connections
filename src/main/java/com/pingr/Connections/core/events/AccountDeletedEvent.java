package com.pingr.Connections.core.events;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.Map;

@JsonSerialize
public class AccountDeletedEvent extends AccountEvent {
    public AccountDeletedEvent() {
    }

    public AccountDeletedEvent(String eventType, Long accountId, Map<String, Object> payload) {
        super(eventType, accountId, payload);
    }
}
