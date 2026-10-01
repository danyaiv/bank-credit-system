package edu.rutmiit.enterprise.bank.api;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public record ClientResponse(
        Long id,
        String fullName,
        String email,
        String passportNumber,
        BigDecimal monthlyIncome,
        BigDecimal currentDebt,
        OffsetDateTime createdAt
) {}