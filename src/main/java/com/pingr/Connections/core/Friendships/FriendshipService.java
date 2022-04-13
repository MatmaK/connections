package com.pingr.Connections.core.Friendships;

import com.pingr.Connections.application.ProducerService;
import com.pingr.Connections.core.Accounts.AccountRepository;
import com.pingr.Connections.core.exceptions.AccountNotFoundException;
import com.pingr.Connections.core.exceptions.FriendshipNotFoundException;
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
        return friendshipRepo.findAllByFriendshipIdentity_FirstIdAccount(idAccount);
    }

    public Integer countFriendsOfOneAccount(Long idAccount) {
        return friendshipRepo.countFriendshipsByFriendshipIdentity_FirstIdAccount(idAccount);
    }

    public Friendship create(FriendshipIdentity friendshipId) throws AccountNotFoundException, IllegalArgumentException {
        if (friendshipId.areSameId())
            throw new IllegalArgumentException("An account can't be friend of itself");

        if (areAlreadyFriends(friendshipId))
            throw new IllegalArgumentException("They are already friends");

        if(! accountsExist(friendshipId))
            throw new AccountNotFoundException();

        try {
            Friendship savedFriendship = friendshipRepo.save(new Friendship(friendshipId));

            //Reverse friendship to they be friends of each other
            Friendship reverseFriendship = new Friendship(friendshipId.reverse());
            friendshipRepo.save(reverseFriendship);

            this.producer.emitFriendshipCreatedEvent(savedFriendship);

            return savedFriendship;
        }
        catch (Exception e) {
            throw new IllegalArgumentException("Something went wrong");
        }

    }

    public void delete(Long idAccount1, Long idAccount2) throws FriendshipNotFoundException {

        Optional<Friendship> optFriendship = friendshipRepo.findById(new FriendshipIdentity(idAccount1, idAccount2));

        if (optFriendship.isEmpty())
            throw new FriendshipNotFoundException(idAccount1, idAccount2);

        friendshipRepo.delete(optFriendship.get());
        Friendship reverseFriendship = new Friendship(optFriendship.get().getFriendshipIdentity().reverse());
        friendshipRepo.delete(reverseFriendship);

        this.producer.emitFriendshipDeletedEvent(optFriendship.get());
    }

    private boolean areAlreadyFriends (FriendshipIdentity friendshipId) {
        return friendshipRepo.existsById(friendshipId);
    }

    private boolean accountsExist(FriendshipIdentity friendshipId) {
        return accountRepo.existsById(friendshipId.getFirstIdAccount())
                && accountRepo.existsById(friendshipId.getSecondIdAccount());
    }
}
