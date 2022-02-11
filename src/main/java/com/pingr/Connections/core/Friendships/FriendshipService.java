package com.pingr.Connections.core.Friendships;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class FriendshipService {
    private FriendshipRepository repo;

    @Autowired
    public FriendshipService(FriendshipRepository repo) {
        this.repo = repo;
    }

    public boolean areAlreadyFriends (Friendship friendship) {
        Optional<Friendship> opt = repo.findFriendshipByIdAccountAppliedAndAndIdAccountReceived(friendship.getIdAccountApplied(), friendship.getIdAccountReceived());
        return opt.isPresent();
    }

    public List<Friendship> findFriendsOfOneAccount(Long idAccount) {
        return repo.findAllFriends(idAccount);
    }

    public Long countFriendsOfOneAccount(Long idAccount) {
        return repo.countAllFriends(idAccount);
    }

    public Friendship create(Friendship friendship) {
        if (friendship.getIdAccountApplied() == friendship.getIdAccountReceived())
            throw new IllegalArgumentException("An account can't be friend of itself");

        if (areAlreadyFriends(friendship))
            throw new IllegalArgumentException("They are already friends");

        return repo.save(friendship);
    }

    public void cancel(Long idAccount1, Long idAccount2) {
        repo.deleteFriendship(idAccount1, idAccount2);
    }
}
