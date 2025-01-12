package com.pingr.Connections.core.exceptions;

public class FriendshipNotFoundException extends RuntimeException {
    public FriendshipNotFoundException() {
        super("Friendship not found");
    }
    public FriendshipNotFoundException(Long id1, Long id2) {
        super("There is no friendship between these IDs: [IDs: " + id1 + " and " + id2 + "]");
    }

    public FriendshipNotFoundException(Long[] accountsIds) {
        super("There is no friendship between these IDs: [IDs: " + accountsIds[0] + " and " + accountsIds[1] + "]");
    }
}
