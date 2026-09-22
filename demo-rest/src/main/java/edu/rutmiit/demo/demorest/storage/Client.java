package edu.rutmiit.demo.demorest.storage;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public class Client {
    private Long id;
    private String fullName;
    private String email;
    private String passportNumber;
    private BigDecimal monthlyIncome;
    private BigDecimal currentDebt;
    private OffsetDateTime createdAt;

    public Client(Long id, String fullName, String email, String passportNumber,
                  BigDecimal monthlyIncome, BigDecimal currentDebt) {
        this.id = id;
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
    public OffsetDateTime getCreatedAt() { return createdAt; }
}