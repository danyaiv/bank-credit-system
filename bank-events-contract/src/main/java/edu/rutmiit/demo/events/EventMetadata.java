package edu.rutmiit.demo.events;

import java.time.Instant;
import java.util.UUID;

/**
 * Метаданные каждого события это его паспорт, которая сопровождает payload.
 *
 * В прод системах вдохновлена спецификацией CloudEvents (cloudevents.io),
 * Мы упростили для лабораторных.Позволяет:
 * - идентифицировать каждое событие (eventId) для дедупликации и трассировки,
 * - знать когда оно произошло (timestamp),
 * - знать кто его отправил (source),
 * - определить тип события (eventType) без десериализации payload.
 */
public record EventMetadata(


        String eventId,


        Instant timestamp,


        String source,


        String eventType
) {
    /**
     * Фабричный метод для создания метаданных на стороне publisher'а.
     * Генерирует UUID и ставит текущее время автоматически.
     */
    public static EventMetadata create(String source, String eventType) {
        return new EventMetadata(
                UUID.randomUUID().toString(),
                Instant.now(),
                source,
                eventType
        );
    }
}
