package edu.rutmiit.enterprise.bank.api;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public record CreateClientRequest(
        @NotBlank(message = "ФИО не может быть пустым")
        String fullName,

        @NotBlank(message = "Email обязателен")
        @Email(message = "Некорректный email")
        String email,

        @NotBlank(message = "Паспортные данные обязательны")
        String passportNumber,

        @NotNull(message = "Доход обязателен")
        @Positive(message = "Доход должен быть положительным числом")
        BigDecimal monthlyIncome,

        @NotNull(message = "Укажите сумму текущих долгов")
        @PositiveOrZero(message = "Долг не может быть отрицательным")
        BigDecimal currentDebt
) {}