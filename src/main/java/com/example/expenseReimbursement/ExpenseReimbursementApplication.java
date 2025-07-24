package com.example.expenseReimbursement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication
@EnableJpaRepositories("com.example.expenseReimbursement.repository")
@EntityScan("com.example.expenseReimbursement.entity")
public class ExpenseReimbursementApplication {
	public static void main(String[] args) {
		SpringApplication.run(ExpenseReimbursementApplication.class, args);
	}
}
