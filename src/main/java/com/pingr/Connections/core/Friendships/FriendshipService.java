package com.pingr.Connections.core.Friendships;

import com.pingr.Connections.core.Accounts.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class FriendshipService {
    private FriendshipRepository friendshipRepo;
    private AccountRepository accountRepo;

    @Autowired
    public FriendshipService(FriendshipRepository friendshipRepo, AccountRepository accountRepo) {
        this.friendshipRepo = friendshipRepo;
        this.accountRepo = accountRepo;
    }

    public boolean areAlreadyFriends (Friendship friendship) {
        Optional<Friendship> opt = friendshipRepo.findFriendship(friendship.getIdAccountApplied(), friendship.getIdAccountReceived());
        return opt.isPresent();
    }

    public List<Friendship> findFriendsOfOneAccount(Long idAccount) {
        return friendshipRepo.findAllFriends(idAccount);
    }

    public Long countFriendsOfOneAccount(Long idAccount) {
        return friendshipRepo.countAllFriends(idAccount);
    }

    public Friendship create(Friendship friendship) {
        if (friendship.getIdAccountApplied() == friendship.getIdAccountReceived())
            throw new IllegalArgumentException("An account can't be friend of itself");

        if (areAlreadyFriends(friendship))
            throw new IllegalArgumentException("They are already friends");

        boolean accountsExist = accountRepo.existsById(friendship.getIdAccountApplied()) && accountRepo.existsById(friendship.getIdAccountReceived());
        if(!accountsExist)
            throw new IllegalArgumentException("Account id not found");

        return friendshipRepo.save(friendship);
    }

    public void cancel(Long idAccount1, Long idAccount2) {
        friendshipRepo.deleteFriendship(idAccount1, idAccount2);
    }
}
