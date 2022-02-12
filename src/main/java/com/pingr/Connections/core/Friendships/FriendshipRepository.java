package com.pingr.Connections.core.Friendships;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface FriendshipRepository extends JpaRepository<Friendship, Long> {
    @Query("SELECT f FROM Friendship f WHERE f.idAccountApplied = ?1 OR f.idAccountReceived = ?1")
    List<Friendship> findAllFriends(Long idAccount);

    @Query("SELECT f FROM Friendship f WHERE f.idAccountApplied in (?1, ?2) AND f.idAccountReceived in (?1, ?2)")
    Optional<Friendship> findFriendship(Long idAccountApplied, Long idAccountReceived);

    @Query("SELECT count(f.id) FROM Friendship f WHERE f.idAccountApplied = ?1 OR f.idAccountReceived = ?1")
    Long countAllFriends(Long idAccount);

    @Transactional
    @Modifying
    @Query("DELETE FROM Friendship f WHERE f.idAccountApplied in (?1, ?2) AND f.idAccountReceived in (?1, ?2)")
    void deleteFriendship(Long idAccount1, Long idAccount2);
}
