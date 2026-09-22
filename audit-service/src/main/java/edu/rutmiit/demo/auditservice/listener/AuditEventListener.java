package edu.rutmiit.demo.auditservice.listener;

import edu.rutmiit.demo.auditservice.config.RabbitMQConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
public class AuditEventListener {

    private static final Logger log = LoggerFactory.getLogger(AuditEventListener.class);

    @RabbitListener(queues = RabbitMQConfig.AUDIT_QUEUE)
    public void handleAnyEvent(Message message) {
        String routingKey = message.getMessageProperties().getReceivedRoutingKey();
        String body = new String(message.getBody(), StandardCharsets.UTF_8);

        log.info(">> [АУДИТ БАНКА]: Зафиксировано событие '{}'. Тело сообщения: {}", routingKey, body);
    }
}