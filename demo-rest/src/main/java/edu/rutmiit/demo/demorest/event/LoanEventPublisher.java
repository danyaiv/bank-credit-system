package edu.rutmiit.demo.demorest.event;

import edu.rutmiit.demo.events.EventEnvelope;
import edu.rutmiit.demo.events.EventMetadata;
import edu.rutmiit.demo.events.LoanEvent;
import edu.rutmiit.demo.events.RoutingKeys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.UUID;

@Component
public class LoanEventPublisher {

    private static final Logger log = LoggerFactory.getLogger(LoanEventPublisher.class);
    private final RabbitTemplate rabbitTemplate;

    public LoanEventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publishLoanCreated(LoanEvent.Created payload) {
        EventMetadata metadata = new EventMetadata(
                UUID.randomUUID().toString(),
                Instant.now(),
                "demo-rest",
                RoutingKeys.LOAN_CREATED
        );

        EventEnvelope<LoanEvent.Created> envelope = new EventEnvelope<>(metadata, payload);
        rabbitTemplate.convertAndSend(RoutingKeys.EXCHANGE, RoutingKeys.LOAN_CREATED, envelope);
        log.info(">> [RabbitMQ]: Событие loan.created отправлено для заявки #{}", payload.applicationId());
    }
}