package com.pingr.Connections.core.events;

import com.pingr.Connections.core.Friendships.Friendship;

import java.util.Map;

public class FriendshipDeletedEvent extends FriendshipEvent{

    protected FriendshipDeletedEvent(Map<String, Object> payload) {
        super(payload);
    }

    public static FriendshipDeletedEvent of(Friendship friendship) {
        return new FriendshipDeletedEvent(
                friendship.toMap()
        );
    }
}
