package com.example.expenseReimbursement.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "transaction")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactionId;

    @ManyToOne
    @JoinColumn(name = "report_id")
    private ExpenseReport report;

    @ManyToOne
    @JoinColumn(name = "paid_by")
    private Employee paidBy;

    private LocalDateTime paidAt;

    private BigDecimal amount;

    private String fromAccount;

    private String toAccount;

    // Default constructor
    public Transaction() {
    }

    // All-args constructor
    public Transaction(Long transactionId, ExpenseReport report, Employee paidBy, LocalDateTime paidAt,
                       BigDecimal amount, String fromAccount, String toAccount) {
        this.transactionId = transactionId;
        this.report = report;
        this.paidBy = paidBy;
        this.paidAt = paidAt;
        this.amount = amount;
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
    }

    // Getters and setters
    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    public ExpenseReport getReport() {
        return report;
    }

    public void setReport(ExpenseReport report) {
        this.report = report;
    }

    public Employee getPaidBy() {
        return paidBy;
    }

    public void setPaidBy(Employee paidBy) {
        this.paidBy = paidBy;
    }

    public LocalDateTime getPaidAt() {
        return paidAt;
    }

    public void setPaidAt(LocalDateTime paidAt) {
        this.paidAt = paidAt;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getFromAccount() {
        return fromAccount;
    }

    public void setFromAccount(String fromAccount) {
        this.fromAccount = fromAccount;
    }

    public String getToAccount() {
        return toAccount;
    }

    public void setToAccount(String toAccount) {
        this.toAccount = toAccount;
    }

    // equals and hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Transaction)) return false;
        Transaction that = (Transaction) o;
        return Objects.equals(transactionId, that.transactionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(transactionId);
    }

    // toString
    @Override
    public String toString() {
        return "Transaction{" +
                "transactionId=" + transactionId +
                ", report=" + (report != null ? report.getReportId() : null) +
                ", paidBy=" + (paidBy != null ? paidBy.getId() : null) +
                ", paidAt=" + paidAt +
                ", amount=" + amount +
                ", fromAccount='" + fromAccount + '\'' +
                ", toAccount='" + toAccount + '\'' +
                '}';
    }
}
