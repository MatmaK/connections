package com.pingr.Connections.core.Accounts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
    private final AccountRepository repo;

    @Autowired
    public AccountService(AccountRepository repo) {
        this.repo = repo;
    }

    public void store(Account account) {
        this.repo.save(account);
    }

    public void remove(Account account) {
        this.repo.deleteById(account.getId());
    }
}
