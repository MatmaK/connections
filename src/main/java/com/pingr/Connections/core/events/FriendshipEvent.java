package com.pingr.Connections.core.events;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

abstract class FriendshipEvent {
    @JsonProperty
    protected final String eventType;

    @JsonProperty
    protected final Long friendshipId;

    @JsonProperty
    protected final Map<String, Object> payload;

    protected FriendshipEvent(Long friendshipId, Map<String, Object> payload) {
        this.eventType = this.getClass().getSimpleName();
        this.friendshipId = friendshipId;
        this.payload = payload;
    }
}
