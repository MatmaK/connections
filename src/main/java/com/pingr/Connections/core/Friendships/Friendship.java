package com.pingr.Connections.core.Friendships;

import javax.persistence.*;
import java.util.Map;

@Entity
@Table
public class Friendship {
    @Id
    @SequenceGenerator(
            name = "friendship_seq_generator",
            sequenceName = "friendship_seq_generator",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "friendship_seq_generator"
    )
    private Long id;

    //Usuário que solicitou a amizade
    @Column(nullable = false)
    private Long idAccountApplied;

    //Usuário que recebeu a amizade
    @Column(nullable = false)
    private Long idAccountReceived;

    public Friendship() {
    }

    public Friendship(Long id, Long idAccountApplied, Long idAccountReceived) {
        this.id = id;
        this.idAccountApplied = idAccountApplied;
        this.idAccountReceived = idAccountReceived;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdAccountApplied() {
        return idAccountApplied;
    }

    public void setIdAccountApplied(Long idAccountApplied) {
        this.idAccountApplied = idAccountApplied;
    }

    public Long getIdAccountReceived() {
        return idAccountReceived;
    }

    public void setIdAccountReceived(Long idAccountReceived) {
        this.idAccountReceived = idAccountReceived;
    }

    public Map<String, Object> toMap() {
        return Map.of(
                "id", id,
                "idAccountApplied", idAccountApplied,
                "idAccountReceived", idAccountReceived
        );
    }
}
