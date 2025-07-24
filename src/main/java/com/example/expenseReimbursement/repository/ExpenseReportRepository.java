//ExpenseReportRepository.java
package com.example.expenseReimbursement.repository;
 
import com.example.expenseReimbursement.entity.ExpenseReport;
import com.example.expenseReimbursement.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
 
public interface ExpenseReportRepository extends JpaRepository<ExpenseReport, Long> {
List<ExpenseReport> findByCreatedBy(Employee employee);
List<ExpenseReport> findByStatus(ExpenseReport.Status status);
// Find reports by employee and status
List<ExpenseReport> findByCreatedByAndStatus(Employee employee, ExpenseReport.Status status);
 
}