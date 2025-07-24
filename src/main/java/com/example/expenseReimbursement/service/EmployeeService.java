package com.example.expenseReimbursement.service;

import com.example.expenseReimbursement.entity.Employee;
import com.example.expenseReimbursement.entity.ExpenseItem;
import com.example.expenseReimbursement.entity.ExpenseReport;
import com.example.expenseReimbursement.repository.EmployeeRepository;
import com.example.expenseReimbursement.repository.ExpenseItemRepository;
import com.example.expenseReimbursement.repository.ExpenseReportRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    private final ExpenseReportRepository expenseReportRepository;
    private final ExpenseItemRepository expenseItemRepository;
    private final EmployeeRepository employeeRepository;

    public EmployeeService(ExpenseReportRepository expenseReportRepository,
                           ExpenseItemRepository expenseItemRepository,
                           EmployeeRepository employeeRepository) {
        this.expenseReportRepository = expenseReportRepository;
        this.expenseItemRepository = expenseItemRepository;
        this.employeeRepository = employeeRepository;
    }

    // ---------- API methods now take employeeId instead of Employee ----------

    public ExpenseReport createReport(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found: " + employeeId));

        ExpenseReport report = new ExpenseReport();
        report.setCreatedBy(employee);
        report.setStatus(ExpenseReport.Status.PENDING);
        report.setTotalAmount(BigDecimal.ZERO);

        return expenseReportRepository.save(report);
    }

    public List<ExpenseReport> getReportsByEmployee(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found: " + employeeId));
        return expenseReportRepository.findByCreatedBy(employee);
    }

    public Optional<ExpenseReport> getReportById(Long id) {
        return expenseReportRepository.findById(id);
    }

    @Transactional
    public ExpenseItem addItemToReport(Long reportId, ExpenseItem item) {
        // Get logged-in user's email
        String email = ((User) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal()).getUsername();

        // Fetch logged-in employee
        Employee employee = employeeRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Logged-in user not found"));

        // Fetch the report
        ExpenseReport report = expenseReportRepository.findById(reportId)
                .orElseThrow(() -> new RuntimeException("Report not found: " + reportId));

        // Check access
        if (!report.getCreatedBy().getId().equals(employee.getId())) {
            throw new RuntimeException("You are not authorized to add items to this report");
        }

        // Proceed as before
        item.setReport(report);
        ExpenseItem savedItem = expenseItemRepository.save(item);

        BigDecimal currentTotal = report.getTotalAmount() != null ? report.getTotalAmount() : BigDecimal.ZERO;
        BigDecimal itemAmount = item.getAmount() != null ? item.getAmount() : BigDecimal.ZERO;
        report.setTotalAmount(currentTotal.add(itemAmount));
        expenseReportRepository.save(report);

        return savedItem;
    }


    public ExpenseReport submitReport(Long reportId) {
        // Get logged-in user's email
        String email = ((User) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal()).getUsername();

        // Fetch logged-in employee
        Employee employee = employeeRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Logged-in user not found"));

        // Fetch the report
        ExpenseReport report = expenseReportRepository.findById(reportId)
                .orElseThrow(() -> new RuntimeException("Report not found: " + reportId));

        // Check access
        if (!report.getCreatedBy().getId().equals(employee.getId())) {
            throw new RuntimeException("You are not authorized to submit this report");
        }

        // Update status
        report.setStatus(ExpenseReport.Status.PENDING);
        return expenseReportRepository.save(report);
    }


    public ExpenseReport createReportByEmail(String email) {
        Employee employee = employeeRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Unauthorized"));

        ExpenseReport report = new ExpenseReport();
        report.setCreatedBy(employee);
        report.setStatus(ExpenseReport.Status.PENDING);
        report.setTotalAmount(BigDecimal.ZERO);
        return expenseReportRepository.save(report);
    }
}