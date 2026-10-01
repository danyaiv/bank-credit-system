package edu.rutmiit.demo.demorest.domain;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Table(name = "clients")
public class ClientEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "passport_number", nullable = false, unique = true)
    private String passportNumber;

    @Column(name = "monthly_income", nullable = false)
    private BigDecimal monthlyIncome;

    @Column(name = "current_debt", nullable = false)
    private BigDecimal currentDebt;

    @Version
    @Column(name = "version", nullable = false)
    private Long version;

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;

    public ClientEntity() {
        this.createdAt = OffsetDateTime.now();
    }

    public ClientEntity(String fullName, String email, String passportNumber, BigDecimal monthlyIncome, BigDecimal currentDebt) {
        this.fullName = fullName;
        this.email = email;
        this.passportNumber = passportNumber;
        this.monthlyIncome = monthlyIncome;
        this.currentDebt = currentDebt;
        this.createdAt = OffsetDateTime.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassportNumber() { return passportNumber; }
    public void setPassportNumber(String passportNumber) { this.passportNumber = passportNumber; }
    public BigDecimal getMonthlyIncome() { return monthlyIncome; }
    public void setMonthlyIncome(BigDecimal monthlyIncome) { this.monthlyIncome = monthlyIncome; }
    public BigDecimal getCurrentDebt() { return currentDebt; }
    public void setCurrentDebt(BigDecimal currentDebt) { this.currentDebt = currentDebt; }
    public Long getVersion() { return version; }
    public void setVersion(Long version) { this.version = version; }
    public OffsetDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(OffsetDateTime createdAt) { this.createdAt = createdAt; }
}