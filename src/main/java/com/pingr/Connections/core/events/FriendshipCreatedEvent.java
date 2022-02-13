package com.pingr.Connections.core.events;

import com.pingr.Connections.core.Friendships.Friendship;

import java.util.HashMap;
import java.util.Map;

public class FriendshipCreatedEvent extends FriendshipEvent{

    protected FriendshipCreatedEvent(Long friendshipId, Map<String, Object> payload) {
        super(friendshipId, payload);
    }

    public static FriendshipCreatedEvent of(Friendship friendship) {
        return new FriendshipCreatedEvent(
                friendship.getId(),
                friendship.toMap()
        );
    }
}
