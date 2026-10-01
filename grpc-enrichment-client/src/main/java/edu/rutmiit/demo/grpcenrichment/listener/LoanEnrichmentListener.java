package edu.rutmiit.demo.grpcenrichment.listener;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.rutmiit.demo.events.EventEnvelope;
import edu.rutmiit.demo.events.EventMetadata;
import edu.rutmiit.demo.events.LoanEvent;
import edu.rutmiit.demo.events.RoutingKeys;
import edu.rutmiit.demo.grpc.BankAnalyticsGrpc;
import edu.rutmiit.demo.grpc.LoanScoringRequest;
import edu.rutmiit.demo.grpc.LoanScoringResponse;
import edu.rutmiit.demo.grpcenrichment.config.RabbitMQConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.UUID;

@Component
public class LoanEnrichmentListener {

    private static final Logger log = LoggerFactory.getLogger(LoanEnrichmentListener.class);

    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;
    private final BankAnalyticsGrpc.BankAnalyticsBlockingStub scoringStub;

    public LoanEnrichmentListener(RabbitTemplate rabbitTemplate,
                                  ObjectMapper objectMapper,
                                  BankAnalyticsGrpc.BankAnalyticsBlockingStub scoringStub) {
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
        this.scoringStub = scoringStub;
    }

    @RabbitListener(queues = RabbitMQConfig.ENRICHMENT_QUEUE)
    public void handleLoanCreated(Message message) {
        try {
            log.info(">> [RabbitMQ]: Получено сообщение на обогащение кредитной заявки");

            byte[] body = message.getBody();
            JsonNode rootNode = objectMapper.readTree(body);
            JsonNode payloadNode = rootNode.get("payload");

            if (payloadNode == null) {
                log.warn("Сообщение не содержит полезной нагрузки payload. Пропуск скоринга.");
                return;
            }

            long applicationId = payloadNode.has("applicationId") ? payloadNode.get("applicationId").asLong() : 1L;
            long clientId = payloadNode.has("clientId") ? payloadNode.get("clientId").asLong() : 1L;
            double amount = payloadNode.has("amount") ? payloadNode.get("amount").asDouble() : 100000.0;

            double monthlyIncome = payloadNode.has("monthlyIncome")
                    ? payloadNode.get("monthlyIncome").asDouble()
                    : 50000.0;

            double currentDebt = payloadNode.has("currentDebt")
                    ? payloadNode.get("currentDebt").asDouble()
                    : 0.0;

            log.info(">> [gRPC ВЫЗОВ]: Отправка заявки #{} на скоринг в BankAnalytics...", applicationId);

            LoanScoringRequest grpcRequest = LoanScoringRequest.newBuilder()
                    .setApplicationId(applicationId)
                    .setClientId(clientId)
                    .setRequestedAmount(amount)
                    .setMonthlyIncome(monthlyIncome)
                    .setCurrentDebt(currentDebt)
                    .build();

            LoanScoringResponse grpcResponse = scoringStub.evaluateLoan(grpcRequest);

            log.info("<< [gRPC ОТВЕТ]: Заявка #{}, Одобрено: {}, Ставка: {}%, Нагрузка: {}%, Причина: {}",
                    applicationId,
                    grpcResponse.getIsApproved(),
                    grpcResponse.getInterestRate(),
                    grpcResponse.getDebtLoadRatio(),
                    grpcResponse.getRejectionReason());

            LoanEvent.Enriched enrichedPayload = new LoanEvent.Enriched(
                    applicationId,
                    clientId,
                    "Клиент #" + clientId,
                    BigDecimal.valueOf(amount),
                    grpcResponse.getIsApproved(),
                    grpcResponse.getInterestRate(),
                    grpcResponse.getDebtLoadRatio(),
                    grpcResponse.getRejectionReason(),
                    OffsetDateTime.now()
            );

            EventMetadata metadata = new EventMetadata(
                    UUID.randomUUID().toString(),
                    Instant.now(),
                    "grpc-enrichment-client",
                    RoutingKeys.LOAN_ENRICHED
            );

            EventEnvelope<LoanEvent.Enriched> envelope = new EventEnvelope<>(metadata, enrichedPayload);

            rabbitTemplate.convertAndSend(RoutingKeys.EXCHANGE, RoutingKeys.LOAN_ENRICHED, envelope);
            log.info(">> [RabbitMQ]: Обогащенное событие loan.enriched успешно опубликовано в брокер!");

        } catch (Exception e) {
            log.error("Ошибка при обработке сообщения скоринга: {}", e.getMessage(), e);
        }
    }
}