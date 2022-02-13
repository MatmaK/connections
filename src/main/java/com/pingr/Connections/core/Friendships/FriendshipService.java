package com.pingr.Connections.core.Friendships;

import com.pingr.Connections.application.ProducerService;
import com.pingr.Connections.core.Accounts.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class FriendshipService {
    private final FriendshipRepository friendshipRepo;
    private final AccountRepository accountRepo;
    private final ProducerService producer;

    @Autowired
    public FriendshipService(FriendshipRepository friendshipRepo, AccountRepository accountRepo, ProducerService producer) {
        this.friendshipRepo = friendshipRepo;
        this.accountRepo = accountRepo;
        this.producer = producer;
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

        if(! accountsExist(friendship))
            throw new IllegalArgumentException("Account id not found");

        Friendship savedFriendship = friendshipRepo.save(friendship);
        this.producer.emitFriendshipCreatedEvent(savedFriendship);
        return savedFriendship;
    }

    public void cancel(Long idAccount1, Long idAccount2) {
        Optional<Friendship> optFriendship = friendshipRepo.findFriendship(idAccount1, idAccount2);
        if (optFriendship.isEmpty())
            throw new IllegalArgumentException("There is no friendship between these IDs");

        this.producer.emitFriendshipDeletedEvent(optFriendship.get());
        friendshipRepo.deleteFriendship(idAccount1, idAccount2);
    }

    private boolean areAlreadyFriends (Friendship friendship) {
        Optional<Friendship> opt = friendshipRepo.findFriendship(friendship.getIdAccountApplied(), friendship.getIdAccountReceived());
        return opt.isPresent();
    }

    private boolean accountsExist(Friendship friendship) {
        return accountRepo.existsById(friendship.getIdAccountApplied()) && accountRepo.existsById(friendship.getIdAccountReceived());
    }
}
