package edu.rutmiit.demo.events;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public interface ClientEvent {
    record Created(
        Long clientId,
        String fullName,
        String email,
        String passportNumber,
        BigDecimal monthlyIncome,
        BigDecimal currentDebt,
        OffsetDateTime createdAt
    ) implements ClientEvent{}

    record Deleted(
            Long clientId,
            OffsetDateTime deletedAt
    ) implements ClientEvent{}
}
