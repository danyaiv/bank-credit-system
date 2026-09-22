package edu.rutmiit.demo.bankapicontract.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Schema (description = "Данные клиента")
public class ClientResponse extends RepresentationModel<ClientResponse> {

    @Schema(description = "ID коиента", example = "1")
    private Long id;

    @Schema(description = "ФИО клиента", example = "Иванов Иван Иванович")
    private String fullName;

    @Schema(description = "Email клиента", example = "ivanov@gmail.com")
    private String email;

    @Schema(description = "Серия и номер паспорта", example = "4510 123456")
    private String passportNumber;

    @Schema(description = "Ежемесячный доход", example = "100000.00")
    private BigDecimal monthlyIncome;

    @Schema(description = "Текущий долг", example = "20000.00")
    private BigDecimal currentDebt;

    @Schema(description = "Дата регистрации")
    private OffsetDateTime createdAt;

    public ClientResponse(){}

    public ClientResponse(Long id, String fullName, String email, String passportNumber, BigDecimal monthlyIncome, BigDecimal currentDebt, OffsetDateTime createdAt) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.passportNumber = passportNumber;
        this.monthlyIncome = monthlyIncome;
        this.currentDebt = currentDebt;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassportNumber() {
        return passportNumber;
    }

    public void setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
    }

    public BigDecimal getMonthlyIncome() {
        return monthlyIncome;
    }

    public void setMonthlyIncome(BigDecimal monthlyIncome) {
        this.monthlyIncome = monthlyIncome;
    }

    public BigDecimal getCurrentDebt() {
        return currentDebt;
    }

    public void setCurrentDebt(BigDecimal currentDebt) {
        this.currentDebt = currentDebt;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
