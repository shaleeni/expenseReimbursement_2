package com.example.expenseReimbursement.dto;

import java.math.BigDecimal;

public class TransactionResponseDTO {
    private Long transactionId;
    private Long reportId;
    private String paidByName;
    private BigDecimal amount;
    private String fromAccount;
    private String toAccount;
    private String status;       // PAID / PENDING etc.
    private String approvedBy;   // Auditor name or id
    private String ifscCode;     // Bank IFSC code
    private String phoneNumber;  // Phone number of employee
    // Getters and Setters

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    public Long getReportId() {
        return reportId;
    }

    public void setReportId(Long reportId) {
        this.reportId = reportId;
    }

    public String getPaidByName() {
        return paidByName;
    }

    public void setPaidByName(String paidByName) {
        this.paidByName = paidByName;
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
}
