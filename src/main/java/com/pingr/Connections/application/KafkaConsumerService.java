package com.pingr.Connections.application;

import com.pingr.Connections.core.Accounts.Account;
import com.pingr.Connections.core.Accounts.AccountService;
import com.pingr.Connections.core.events.AccountCreatedEvent;
import com.pingr.Connections.core.events.AccountDeletedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {

    @Autowired
    private AccountService service;

    @KafkaListener(
            containerFactory = "accountCreatedEventKafkaListenerContainerFactory",
            topics = "${topics.acc_created}",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void handleAccountCreation(AccountCreatedEvent event) {
        Account account = event.extract();
        this.service.store(account);
    }

    @KafkaListener(
            containerFactory = "accountDeletedEventKafkaListenerContainerFactory",
            topics = "${topics.acc_deleted}",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void handleAccountDeletion(AccountDeletedEvent event) {
        Account account = event.extract();
        this.service.remove(account);
    }
}
