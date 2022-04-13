package com.pingr.Connections.core.Friendships;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/friendships")
public class FriendshipController {

    private FriendshipService service;

    @Autowired
    public FriendshipController(FriendshipService service) {
        this.service = service;
    }

    @GetMapping("/{idAccount}")
    public List<Friendship> findFriendsOfOneAccount(@PathVariable("idAccount") Long idAccount) {
        return service.findFriendsOfOneAccount(idAccount);
    }

    @GetMapping("/count/{idAccount}")
    public Integer countFriendsOfOneAccount(@PathVariable("idAccount") Long idAccount) {
        return service.countFriendsOfOneAccount(idAccount);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Friendship create(@RequestBody FriendshipIdentity friendshipId) {
        return service.create(friendshipId);
    }

    @DeleteMapping("/{idAccount1}/{idAccount2}")
    public void cancel(@PathVariable("idAccount1") Long idAccount1, @PathVariable("idAccount2") Long idAccount2) {
        service.delete(idAccount1, idAccount2);
    }
}
