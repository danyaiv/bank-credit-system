package edu.rutmiit.demo.bankapicontract.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;

@Schema (description = "Запрос на создание / регистрацию клиента банка")
public record ClientRequest(
        @Schema (description =" ФИО клинта", example = "Иванов Иван Иванович")
        @NotBlank
        @Size (max = 255, message = "ФИО не должно превышать 255 символов")
        String name,

        @Schema (description =" Почта клиента", example = "ivanov@gmail.com")
        @NotBlank
        @Email(message = "некорректный формат email")
        String email,

        @Schema (description =" Серия и номер пасспорта", example = "4510 123456")
        @NotBlank
        String passportNumber,

        @Schema (description ="Ежемесячный доход", example = "100000.00")
        @Positive (message = "Доход должен быть положительным числом")
        BigDecimal monthlyIncome,

        @Schema (description ="Сумма текущих непогашенных долгов", example = "20000.00")
        @PositiveOrZero (message = "Долг не может быть отрицательным")
        BigDecimal currentDebt
) { }