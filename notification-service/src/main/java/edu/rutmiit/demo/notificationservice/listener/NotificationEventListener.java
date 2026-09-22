package edu.rutmiit.demo.notificationservice.listener;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.rutmiit.demo.events.RoutingKeys;
import edu.rutmiit.demo.notificationservice.config.RabbitMQConfig;
import edu.rutmiit.demo.notificationservice.websocket.NotificationWebSocketHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Component
public class NotificationEventListener {

    private static final Logger log = LoggerFactory.getLogger(NotificationEventListener.class);

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final NotificationWebSocketHandler webSocketHandler;

    public NotificationEventListener(NotificationWebSocketHandler webSocketHandler) {
        this.webSocketHandler = webSocketHandler;
    }

    @RabbitListener(queues = RabbitMQConfig.NOTIFICATION_QUEUE)
    public void handleNotification(Message message) {
        try {
            String routingKey = message.getMessageProperties().getReceivedRoutingKey();
            String rawJson = new String(message.getBody(), StandardCharsets.UTF_8);
            JsonNode root = objectMapper.readTree(rawJson);
            JsonNode payload = root.get("payload");

            String title = "Уведомление Банка";
            String text = "Событие: " + routingKey;

            if (payload != null) {
                if (RoutingKeys.CLIENT_CREATED.equals(routingKey)) {
                    title = "Новый клиент зарегистрирован";
                    String name = payload.has("fullName") ? payload.get("fullName").asText() : "Новый клиент";
                    String income = payload.has("monthlyIncome") ? payload.get("monthlyIncome").asText() : "0";
                    text = "Клиент: " + name + " (Доход: " + income + " руб)";
                } else if (RoutingKeys.LOAN_CREATED.equals(routingKey)) {
                    title = "Подана новая заявка на кредит";
                    String appId = payload.has("applicationId") ? payload.get("applicationId").asText() : "";
                    String amount = payload.has("amount") ? payload.get("amount").asText() : "";
                    text = "Заявка #" + appId + " на сумму " + amount + " руб направлена на скоринг";
                } else if (RoutingKeys.LOAN_ENRICHED.equals(routingKey)) {
                    boolean isApproved = payload.has("isApproved") && payload.get("isApproved").asBoolean();
                    String appId = payload.has("applicationId") ? payload.get("applicationId").asText() : "";
                    if (isApproved) {
                        title = "Кредитная заявка ОДОБРЕНА!";
                        String rate = payload.has("interestRate") ? payload.get("interestRate").asText() : "13.5";
                        text = "Заявка #" + appId + ". Персональная ставка: " + rate + "%";
                    } else {
                        title = "ОТКАЗ по кредитной заявке";
                        String reason = payload.has("rejectionReason") ? payload.get("rejectionReason").asText() : "Высокая долговая нагрузка";
                        text = "Заявка #" + appId + ". Причина: " + reason;
                    }
                }
            }

            log.info(">> [WebSocket PUSH]: '{}' -> {}", title, text);


            Map<String, Object> wsPayload = new HashMap<>();
            wsPayload.put("type", "NOTIFICATION");
            wsPayload.put("title", title);
            wsPayload.put("text", text);
            wsPayload.put("message", text);
            wsPayload.put("routingKey", routingKey);
            wsPayload.put("eventType", routingKey);
            wsPayload.put("source", "bank-system");
            wsPayload.put("timestamp", java.time.LocalTime.now().format(java.time.format.DateTimeFormatter.ofPattern("HH:mm:ss")));

            String jsonToSend = objectMapper.writeValueAsString(wsPayload);
            webSocketHandler.broadcast(jsonToSend);

        } catch (Exception e) {
            log.error("Ошибка при обработке уведомления: {}", e.getMessage(), e);
        }
    }
}