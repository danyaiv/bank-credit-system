package edu.rutmiit.demo.bankapicontract.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.hateoas.RepresentationModel;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Schema(description = "Информация о кредитной заявке")
public class LoanApplicationResponse extends RepresentationModel<LoanApplicationResponse> {

    @Schema(description = "ID кредитной заявки", example = "10")
    private Long id;

    @Schema(description = "ID клиента", example = "1")
    private Long clientId;

    @Schema(description = "Сумма кредита", example = "300000.00")
    private BigDecimal amount;

    @Schema(description = "Срок кредита в месяцах", example = "24")
    private Integer termMonths;

    @Schema(description = "Цель кредита", example = "Покупка автомобиля")
    private String purpose;

    @Schema(description = "Статус заявки", example = "PENDING")
    private String status;

    @Schema(description = "Одобренная процентная ставка (%)", example = "14.5")
    private Double interestRate;

    @Schema(description = "Причина отказа (если статус REJECTED)", example = "Долговая нагрузка превышает 50%")
    private String rejectionReason;

    @Schema(description = "Дата создания заявки")
    private OffsetDateTime createdAt;

    public LoanApplicationResponse() {}

    public LoanApplicationResponse(Long id, Long clientId, BigDecimal amount, Integer termMonths,
                                   String purpose, String status, Double interestRate,
                                   String rejectionReason, OffsetDateTime createdAt) {
        this.id = id;
        this.clientId = clientId;
        this.amount = amount;
        this.termMonths = termMonths;
        this.purpose = purpose;
        this.status = status;
        this.interestRate = interestRate;
        this.rejectionReason = rejectionReason;
        this.createdAt = createdAt;
    }

    // Геттеры и сеттеры
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getClientId() { return clientId; }
    public void setClientId(Long clientId) { this.clientId = clientId; }
    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    public Integer getTermMonths() { return termMonths; }
    public void setTermMonths(Integer termMonths) { this.termMonths = termMonths; }
    public String getPurpose() { return purpose; }
    public void setPurpose(String purpose) { this.purpose = purpose; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public Double getInterestRate() { return interestRate; }
    public void setInterestRate(Double interestRate) { this.interestRate = interestRate; }
    public String getRejectionReason() { return rejectionReason; }
    public void setRejectionReason(String rejectionReason) { this.rejectionReason = rejectionReason; }
    public OffsetDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(OffsetDateTime createdAt) { this.createdAt = createdAt; }
}