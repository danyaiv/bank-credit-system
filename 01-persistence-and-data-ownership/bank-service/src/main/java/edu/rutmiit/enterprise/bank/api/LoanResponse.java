package edu.rutmiit.enterprise.bank.api;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public record LoanResponse(
        Long id,
        Long clientId,
        BigDecimal amount,
        Integer termMonths,
        String purpose,
        String status,
        Double interestRate,
        Double debtLoadRatio,
        String rejectionReason,
        OffsetDateTime createdAt
) {}