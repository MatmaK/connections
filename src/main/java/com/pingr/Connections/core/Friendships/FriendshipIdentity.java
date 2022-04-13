package com.pingr.Connections.core.Friendships;

import com.sun.istack.NotNull;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class FriendshipIdentity implements Serializable {
    @NotNull
    Long firstIdAccount;

    @NotNull
    Long secondIdAccount;

    public FriendshipIdentity() {
    }

    public FriendshipIdentity(Long firstIdAccount, Long secondIdAccount) {
        this.firstIdAccount = firstIdAccount;
        this.secondIdAccount = secondIdAccount;
    }

    public Long getFirstIdAccount() {
        return firstIdAccount;
    }

    public void setFirstIdAccount(Long firstIdAccount) {
        this.firstIdAccount = firstIdAccount;
    }

    public Long getSecondIdAccount() {
        return secondIdAccount;
    }

    public void setSecondIdAccount(Long secondIdAccount) {
        this.secondIdAccount = secondIdAccount;
    }

    public Boolean areSameId() {
        return this.firstIdAccount == this.secondIdAccount;
    }

    public FriendshipIdentity reverse() {
        return new FriendshipIdentity(this.secondIdAccount, this.firstIdAccount);
    }
}
