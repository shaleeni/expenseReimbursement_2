// ExpenseItemRepository.java
package com.example.expenseReimbursement.repository;

import com.example.expenseReimbursement.entity.ExpenseItem;
import com.example.expenseReimbursement.entity.ExpenseReport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExpenseItemRepository extends JpaRepository<ExpenseItem, Long> {
    List<ExpenseItem> findByReport(ExpenseReport report);
}