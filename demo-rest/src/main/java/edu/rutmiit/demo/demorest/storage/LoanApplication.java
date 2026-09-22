package edu.rutmiit.demo.demorest.storage;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public class LoanApplication {
    private Long id;
    private Long clientId;
    private BigDecimal amount;
    private Integer termMonths;
    private String purpose;
    private String status; // PENDING, APPROVED, REJECTED
    private Double interestRate;
    private String rejectionReason;
    private OffsetDateTime createdAt;

    public LoanApplication(Long id, Long clientId, BigDecimal amount, Integer termMonths, String purpose) {
        this.id = id;
        this.clientId = clientId;
        this.amount = amount;
        this.termMonths = termMonths;
        this.purpose = purpose;
        this.status = "PENDING";
        this.interestRate = 0.0;
        this.rejectionReason = "";
        this.createdAt = OffsetDateTime.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getClientId() { return clientId; }
    public BigDecimal getAmount() { return amount; }
    public Integer getTermMonths() { return termMonths; }
    public String getPurpose() { return purpose; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public Double getInterestRate() { return interestRate; }
    public void setInterestRate(Double interestRate) { this.interestRate = interestRate; }
    public String getRejectionReason() { return rejectionReason; }
    public void setRejectionReason(String rejectionReason) { this.rejectionReason = rejectionReason; }
    public OffsetDateTime getCreatedAt() { return createdAt; }
}