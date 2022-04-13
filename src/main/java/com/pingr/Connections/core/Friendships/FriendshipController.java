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

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Friendship create(@RequestBody FriendshipIdentity friendshipId) {
        return service.create(friendshipId);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteFriendship(@RequestParam Long[] accountsIds) {
        service.delete(accountsIds);
    }
}
