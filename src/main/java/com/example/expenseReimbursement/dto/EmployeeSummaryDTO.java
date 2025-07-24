package com.example.expenseReimbursement.dto;

public class EmployeeSummaryDTO {
    private Long id;
    private String name;

    public EmployeeSummaryDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

 // Getters and setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

    
    
}
