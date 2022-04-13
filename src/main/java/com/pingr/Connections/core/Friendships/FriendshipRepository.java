package com.pingr.Connections.core.Friendships;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface FriendshipRepository extends JpaRepository<Friendship, FriendshipIdentity> {
    Integer countFriendshipsByFriendshipIdentity_FirstIdAccount(Long id);

    List<Friendship> findAllByFriendshipIdentity_FirstIdAccount(Long id);
}
