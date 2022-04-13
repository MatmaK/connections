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

    public List<Friendship> findFriendsOfOneAccount(Long idAccount) throws AccountNotFoundException {
        if (!accountRepo.existsById(idAccount))
            throw new AccountNotFoundException(idAccount);

        return friendshipRepo.findAllByFriendshipIdentity_FirstIdAccount(idAccount);
    }

    public Integer countFriendsOfOneAccount(Long idAccount) throws AccountNotFoundException {
        if (!accountRepo.existsById(idAccount))
            throw new AccountNotFoundException(idAccount);

        return friendshipRepo.countFriendshipsByFriendshipIdentity_FirstIdAccount(idAccount);
    }

    public Friendship create(FriendshipIdentity friendshipId) {
        validateNewFriendship(friendshipId);

        Friendship savedFriendship = friendshipRepo.save(new Friendship(friendshipId));

        //Reverse friendship to they be friends of each other
        Friendship reverseFriendship = new Friendship(friendshipId.reverse());
        friendshipRepo.save(reverseFriendship);

        this.producer.emitFriendshipCreatedEvent(savedFriendship);

        return savedFriendship;
    }

    public void delete(Long[] accountsIds) {
        Friendship friendship = new Friendship(new FriendshipIdentity(accountsIds));
        validateDeleteFriendship(friendship);

        friendshipRepo.delete(friendship);
        Friendship reverseFriendship = new Friendship(friendship.getFriendshipIdentity().reverse());
        friendshipRepo.delete(reverseFriendship);

        this.producer.emitFriendshipDeletedEvent(friendship);
    }

    private boolean areAlreadyFriends (FriendshipIdentity friendshipId) {
        return friendshipRepo.existsById(friendshipId);
    }

    private boolean accountsExist(FriendshipIdentity friendshipId) {
        return accountRepo.existsById(friendshipId.getFirstIdAccount())
                && accountRepo.existsById(friendshipId.getSecondIdAccount());
    }

    private void validateNewFriendship(FriendshipIdentity friendshipId) throws AccountNotFoundException, IllegalArgumentException {
        if (areAlreadyFriends(friendshipId))
            throw new IllegalArgumentException("They are already friends");

        if (!accountsExist(friendshipId))
            throw new AccountNotFoundException();

        if (friendshipId.areSameId())
            throw new IllegalArgumentException("An account can't be friend of itself");
    }

    private void validateDeleteFriendship(Friendship friendship) throws FriendshipNotFoundException, AccountNotFoundException {
        if(!accountsExist(friendship.getFriendshipIdentity()))
            throw new AccountNotFoundException();

        if (!friendshipRepo.existsById(friendship.getFriendshipIdentity()))
            throw new FriendshipNotFoundException(friendship.getFriendshipIdentity().arrayOfAccountsIds());
    }
}
