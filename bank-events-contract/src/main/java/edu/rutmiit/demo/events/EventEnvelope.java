package edu.rutmiit.demo.events;

/**
 * Универсальный конверт для событий Recruitment API.
 * Разделяет технические данные (метаданные) и бизнес-данные (полезную нагрузку).
 *
 * @param <T> тип события (VacancyEvent или CandidateEvent)
 */
public record EventEnvelope<T>(
        EventMetadata metadata,
        T payload
) {
    /**
     * Создает новый конверт, автоматически генерируя UUID и метку времени.
     *
     * @param payload   само событие (например, VacancyEvent.Created)
     * @param source    имя сервиса-отправителя (например, "hr-service")
     * @param eventType тип события для маршрутизации (из RoutingKeys)
     */
    public static <T> EventEnvelope<T> wrap(T payload, String source, String eventType) {
        return new EventEnvelope<>(
                EventMetadata.create(source, eventType),
                payload
        );
    }
}