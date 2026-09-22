package edu.rutmiit.demo.events;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public interface LoanEvent {

    // 1. Клиент нажал «Подать заявку на кредит»
    record Created(
            Long applicationId,
            Long clientId,
            BigDecimal amount,
            Integer termMonths,
            String purpose,
            OffsetDateTime createdAt
    ) implements LoanEvent {}

    // 2. Сервис обогащения вызвал gRPC скоринг и получил вердикт
    record Enriched(
            Long applicationId,
            Long clientId,
            String clientFullName,
            BigDecimal amount,
            boolean isApproved,
            Double interestRate,
            Double debtLoadRatio,
            String rejectionReason,
            OffsetDateTime evaluatedAt
    ) implements LoanEvent {}
}