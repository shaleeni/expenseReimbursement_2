package com.example.expenseReimbursement.dto;

import java.math.BigDecimal;

public class ExpenseReportDTO {
    private Long reportId;
    private String status;
    private BigDecimal totalAmount;
    private EmployeeSummaryDTO createdBy;
    private EmployeeSummaryDTO reviewedBy;

    public ExpenseReportDTO(Long reportId, String status, BigDecimal totalAmount,
                            EmployeeSummaryDTO createdBy, EmployeeSummaryDTO reviewedBy) {
        this.reportId = reportId;
        this.status = status;
        this.totalAmount = totalAmount;
        this.createdBy = createdBy;
        this.reviewedBy = reviewedBy;
    }
 // Getters and setters
	public Long getReportId() {
		return reportId;
	}

	public void setReportId(Long reportId) {
		this.reportId = reportId;
	}

	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public EmployeeSummaryDTO getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(EmployeeSummaryDTO createdBy) {
		this.createdBy = createdBy;
	}

	public EmployeeSummaryDTO getReviewedBy() {
		return reviewedBy;
	}

	public void setReviewedBy(EmployeeSummaryDTO reviewedBy) {
		this.reviewedBy = reviewedBy;
	}

    
    
}
