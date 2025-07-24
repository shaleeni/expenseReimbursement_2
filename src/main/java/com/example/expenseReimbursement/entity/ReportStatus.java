package com.example.expenseReimbursement.entity;
 
 
	public enum ReportStatus {
		DRAFT,
	    PENDING , // Report has been submitted and waiting for audit
	    APPROVED,        // Report has been approved by auditor
	    REJECTED         // Report has been rejected by auditor
	}
