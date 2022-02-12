package com.pingr.Connections.core.Accounts;

import javax.persistence.*;

@Entity
@Table
public class Account {
    @Id
    private Long id;

    public Account() {
    }

    public Account(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                '}';
    }
}
