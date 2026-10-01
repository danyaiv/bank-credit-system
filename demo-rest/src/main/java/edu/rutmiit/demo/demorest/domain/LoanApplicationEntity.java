package edu.rutmiit.demo.demorest.domain;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Table(name = "loan_applications")
public class LoanApplicationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "client_id", nullable = false)
    private Long clientId;

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @Column(name = "term_months", nullable = false)
    private Integer termMonths;

    @Column(name = "purpose", nullable = false)
    private String purpose;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private LoanStatus status;

    @Column(name = "interest_rate")
    private Double interestRate;

    @Column(name = "debt_load_ratio")
    private Double debtLoadRatio;

    @Column(name = "rejection_reason")
    private String rejectionReason;

    @Version
    @Column(name = "version", nullable = false)
    private Long version;

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;

    public LoanApplicationEntity() {
        this.status = LoanStatus.PENDING;
        this.createdAt = OffsetDateTime.now();
    }

    public LoanApplicationEntity(Long clientId, BigDecimal amount, Integer termMonths, String purpose) {
        this.clientId = clientId;
        this.amount = amount;
        this.termMonths = termMonths;
        this.purpose = purpose;
        this.status = LoanStatus.PENDING;
        this.createdAt = OffsetDateTime.now();
    }

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
    public LoanStatus getStatus() { return status; }
    public void setStatus(LoanStatus status) { this.status = status; }
    public Double getInterestRate() { return interestRate; }
    public void setInterestRate(Double interestRate) { this.interestRate = interestRate; }
    public Double getDebtLoadRatio() { return debtLoadRatio; }
    public void setDebtLoadRatio(Double debtLoadRatio) { this.debtLoadRatio = debtLoadRatio; }
    public String getRejectionReason() { return rejectionReason; }
    public void setRejectionReason(String rejectionReason) { this.rejectionReason = rejectionReason; }
    public Long getVersion() { return version; }
    public void setVersion(Long version) { this.version = version; }
    public OffsetDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(OffsetDateTime createdAt) { this.createdAt = createdAt; }
}