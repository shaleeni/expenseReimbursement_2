package com.example.expenseReimbursement.entity;
import com.example.expenseReimbursement.entity.Employee;
import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "expense_report")
public class ExpenseReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reportId;

    @ManyToOne
    @JoinColumn(name = "created_by",referencedColumnName = "id")
    private Employee createdBy;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne
    @JoinColumn(name = "reviewed_by")
    private Employee reviewedBy;

    private BigDecimal totalAmount;

    public enum Status {
        PENDING, APPROVED, DECLINED, PAID
    }

    // -------- Constructors --------
    public ExpenseReport() {}

    public ExpenseReport(Long reportId, Employee createdBy, Status status,
                         Employee reviewedBy, BigDecimal totalAmount) {
        this.reportId = reportId;
        this.createdBy = createdBy;
        this.status = status;
        this.reviewedBy = reviewedBy;
        this.totalAmount = totalAmount;
    }

    // -------- Getters & Setters --------
    public Long getReportId() {
        return reportId;
    }

    public void setReportId(Long reportId) {
        this.reportId = reportId;
    }

    public Employee getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Employee createdBy) {
        this.createdBy = createdBy;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Employee getReviewedBy() {
        return reviewedBy;
    }

    public void setReviewedBy(Employee reviewedBy) {
        this.reviewedBy = reviewedBy;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }
}
