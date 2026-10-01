package edu.rutmiit.enterprise.bank.api;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public record CreateLoanRequest(
        @NotNull(message = "ID клиента обязателен")
        Long clientId,

        @NotNull(message = "Сумма кредита обязательна")
        @Min(value = 10000, message = "Минимальная сумма кредита 10 000 руб")
        BigDecimal amount,

        @NotNull(message = "Срок кредита обязателен")
        @Min(value = 3, message = "Минимальный срок — 3 месяца")
        @Max(value = 120, message = "Максимальный срок — 120 месяцев")
        Integer termMonths,

        @NotBlank(message = "Укажите цель кредита")
        String purpose
) {}