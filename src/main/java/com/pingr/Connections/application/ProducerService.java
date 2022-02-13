package com.pingr.Connections.application;

import com.pingr.Connections.core.Friendships.Friendship;
import com.pingr.Connections.core.events.FriendshipCreatedEvent;
import com.pingr.Connections.core.events.FriendshipDeletedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ProducerService {
    @Value(value = "${topics.fshp_establd}")
    private String friendshipCreatedTopic;

    @Value(value = "${topics.fshp_deleted}")
    private String friendshipDeletedTopic;

    @Autowired // injeção de dependências
    private KafkaTemplate<String, Object> template;

    public void emitFriendshipCreatedEvent(Friendship friendship) {
        this.template.send(
                this.friendshipCreatedTopic,
                FriendshipCreatedEvent.of(friendship)
        );
    }

    public void emitFriendshipDeletedEvent(Friendship friendship) {
        this.template.send(
                this.friendshipDeletedTopic,
                FriendshipDeletedEvent.of(friendship)
        );
    }
}
