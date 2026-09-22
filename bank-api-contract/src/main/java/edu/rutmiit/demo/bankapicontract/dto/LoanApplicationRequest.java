package edu.rutmiit.demo.bankapicontract.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;

@Schema(description = "Заявка на получение кредита")
public record LoanApplicationRequest(
        @Schema(description = "ID клиента-заявителя", example = "1")
        @NotNull(message = "ID клиента обязателен")
        Long clientId,

        @Schema(description = "Запрашиваемая сумма кредита", example = "300000.00")
        @Min(value = 10000, message = "Минимальная сумма кредита 10 000 руб")
        BigDecimal amount,

        @Schema(description = "Срок кредита в месяцах", example = "24")
        @NotNull(message = "Срок кредита обязателен")
        @Min(value = 3, message = "Минимальный срок — 3 месяца")
        @Max(value = 120, message = "Максимальный срок — 120 месяцев")
        Integer termMonths,

        @Schema(description = "Цель кредита", example = "Покупка автомобиля")
        @NotBlank(message = "Укажите цель кредита")
        String purpose
) {}