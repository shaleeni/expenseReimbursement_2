package com.example.expenseReimbursement.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ExpenseItemDTO {
    private Long itemId;
    private ExpenseReportDTO report;
    private BigDecimal amount;
    private LocalDate dateOfExpense;
    private String invoiceNumber;

    public ExpenseItemDTO(Long itemId, ExpenseReportDTO report, BigDecimal amount,
                          LocalDate dateOfExpense, String invoiceNumber) {
        this.itemId = itemId;
        this.report = report;
        this.amount = amount;
        this.dateOfExpense = dateOfExpense;
        this.invoiceNumber = invoiceNumber;
    }

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public ExpenseReportDTO getReport() {
		return report;
	}

	public void setReport(ExpenseReportDTO report) {
		this.report = report;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public LocalDate getDateOfExpense() {
		return dateOfExpense;
	}

	public void setDateOfExpense(LocalDate dateOfExpense) {
		this.dateOfExpense = dateOfExpense;
	}

	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

    // Getters and Setters
    
    
}
