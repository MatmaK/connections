package com.pingr.Connections.core.Friendships;

import javax.persistence.*;
import java.util.Map;

@Entity
@Table
public class Friendship {
    @EmbeddedId
    private FriendshipIdentity friendshipIdentity;

    public Friendship() {
    }

    public Friendship(FriendshipIdentity friendshipIdentity) {
        this.friendshipIdentity = friendshipIdentity;
    }

    public FriendshipIdentity getFriendshipIdentity() {
        return friendshipIdentity;
    }

    public Map<String, Object> toMap() {
        return Map.of(
                "firstIdAccount", this.friendshipIdentity.getFirstIdAccount(),
                "secondIdAccount", this.friendshipIdentity.getSecondIdAccount()
        );
    }
}
