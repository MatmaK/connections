package com.pingr.Connections.core.events;

import com.pingr.Connections.core.Friendships.Friendship;

import java.util.Map;

public class FriendshipCreatedEvent extends FriendshipEvent{

    protected FriendshipCreatedEvent(Map<String, Object> payload) {
        super(payload);
    }

    public static FriendshipCreatedEvent of(Friendship friendship) {
        return new FriendshipCreatedEvent(
                friendship.toMap()
        );
    }
}
