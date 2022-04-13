package com.pingr.Connections.core.Accounts;

import com.pingr.Connections.core.Friendships.Friendship;
import com.pingr.Connections.core.Friendships.FriendshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/accounts")
public class AccountController {

    private FriendshipService service;

    @Autowired
    public AccountController(FriendshipService service) {
        this.service = service;
    }

    @GetMapping("/{id}/friends")
    public List<Friendship> findFriendsOfOneAccount(@PathVariable("id") Long id) {
        return service.findFriendsOfOneAccount(id);
    }

    @GetMapping("/{id}/friends/count")
    public Integer countFriendsOfOneAccount(@PathVariable("id") Long id) {
        return service.countFriendsOfOneAccount(id);
    }
}
