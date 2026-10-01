package edu.rutmiit.enterprise.bank.domain;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Table(name = "loan_applications")
public class LoanApplicationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)
    private ClientEntity client;

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

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;

    public LoanApplicationEntity() {
        this.status = LoanStatus.PENDING;
        this.createdAt = OffsetDateTime.now();
    }

    public LoanApplicationEntity(ClientEntity client, BigDecimal amount, Integer termMonths, String purpose) {
        this.client = client;
        this.amount = amount;
        this.termMonths = termMonths;
        this.purpose = purpose;
        this.status = LoanStatus.PENDING;
        this.createdAt = OffsetDateTime.now();
    }

    // Геттеры и сеттеры
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public ClientEntity getClient() { return client; }
    public void setClient(ClientEntity client) { this.client = client; }
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
    public OffsetDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(OffsetDateTime createdAt) { this.createdAt = createdAt; }
}