package edu.rutmiit.demo.grpcenrichment.publisher;

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
public class EnrichmentEventPublisher {

    private static final Logger log = LoggerFactory.getLogger(EnrichmentEventPublisher.class);
    private final RabbitTemplate rabbitTemplate;

    public EnrichmentEventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publishLoanEnriched(LoanEvent.Enriched payload) {
        EventMetadata metadata = new EventMetadata(
                UUID.randomUUID().toString(),
                Instant.now(),
                "grpc-enrichment-client",
                RoutingKeys.LOAN_ENRICHED
        );

        EventEnvelope<LoanEvent.Enriched> envelope = new EventEnvelope<>(metadata, payload);

        rabbitTemplate.convertAndSend(RoutingKeys.EXCHANGE, RoutingKeys.LOAN_ENRICHED, envelope);
        log.info(">> [RabbitMQ]: Обогащенное событие loan.enriched успешно отправлено через EnrichmentEventPublisher!");
    }
}