package edu.rutmiit.demo.bankapicontract.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

@Schema(description = "Запрос на частичное обновление данных клиента")
public record PatchClientRequest(
        @Schema(description = "Новый ежемесячный доход", example = "120000.00")
        @Positive(message = "Доход должен быть положительным числом")
        BigDecimal monthlyIncome,

        @Schema(description = "Обновленная сумма текущих долгов", example = "5000.00")
        @PositiveOrZero(message = "Сумма долга не может быть отрицательной")
        BigDecimal currentDebt
) {}