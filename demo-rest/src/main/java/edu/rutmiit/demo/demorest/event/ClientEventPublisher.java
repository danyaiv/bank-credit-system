package edu.rutmiit.demo.demorest.event;

import edu.rutmiit.demo.events.ClientEvent;
import edu.rutmiit.demo.events.EventEnvelope;
import edu.rutmiit.demo.events.EventMetadata;
import edu.rutmiit.demo.events.RoutingKeys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.UUID;

@Component
public class ClientEventPublisher {

    private static final Logger log = LoggerFactory.getLogger(ClientEventPublisher.class);
    private final RabbitTemplate rabbitTemplate;

    public ClientEventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publishClientCreated(ClientEvent.Created payload) {
        EventMetadata metadata = new EventMetadata(
                UUID.randomUUID().toString(),
                Instant.now(),
                "demo-rest",
                RoutingKeys.CLIENT_CREATED
        );

        EventEnvelope<ClientEvent.Created> envelope = new EventEnvelope<>(metadata, payload);
        rabbitTemplate.convertAndSend(RoutingKeys.EXCHANGE, RoutingKeys.CLIENT_CREATED, envelope);
        log.info(">> [RabbitMQ]: Событие client.created отправлено для клиента #{}", payload.clientId());
    }
}