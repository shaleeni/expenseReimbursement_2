package com.example.expenseReimbursement.service;

import com.example.expenseReimbursement.entity.Employee;
import com.example.expenseReimbursement.entity.ExpenseReport;
import com.example.expenseReimbursement.repository.EmployeeRepository;
import com.example.expenseReimbursement.repository.ExpenseReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import java.util.List;
import java.util.Optional;

@Service
public class AuditService {

    @Autowired
    private ExpenseReportRepository expenseReportRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    // Get reports by status
    public List<ExpenseReport> getReportsByStatus(ExpenseReport.Status status) {
        getLoggedInAuditor(); // role check
        return expenseReportRepository.findByStatus(status);
    }

    // Get pending reports
    public List<ExpenseReport> getPendingReports() {
        getLoggedInAuditor(); // role check
        return getReportsByStatus(ExpenseReport.Status.PENDING);
    }

    // Get specific report for audit (if pending)
    public Optional<ExpenseReport> getReportForAudit(Long reportId) {
        getLoggedInAuditor(); // role check
        Optional<ExpenseReport> report = expenseReportRepository.findById(reportId);
        return report.filter(r -> r.getStatus() == ExpenseReport.Status.PENDING);
    }

    // Approve a report
    public ExpenseReport approveReport(Long reportId) {
        return reviewReport(reportId, ExpenseReport.Status.APPROVED);
    }

    // Decline a report
    public ExpenseReport declineReport(Long reportId) {
        return reviewReport(reportId, ExpenseReport.Status.DECLINED);
    }

    // Shared logic for approving/declining by logged-in user
    private ExpenseReport reviewReport(Long reportId, ExpenseReport.Status newStatus) {
        ExpenseReport report = expenseReportRepository.findById(reportId)
                .orElseThrow(() -> new RuntimeException("Report not found"));

        if (report.getStatus() != ExpenseReport.Status.PENDING) {
            throw new RuntimeException("Only pending reports can be reviewed.");
        }

        Employee auditor = getLoggedInAuditor();

        if (report.getCreatedBy().getId().equals(auditor.getId())) {
            throw new RuntimeException("You cannot review your own expense report.");
        }

        report.setStatus(newStatus);
        report.setReviewedBy(auditor);

        return expenseReportRepository.save(report);
    }

    // Shared logic for approving/declining (legacy support with auditorId param)
    private ExpenseReport reviewReport(Long reportId, Long auditorId, ExpenseReport.Status newStatus) {
        getLoggedInAuditor(); // block unauthorized access even if ID is passed manually

        ExpenseReport report = expenseReportRepository.findById(reportId)
                .orElseThrow(() -> new RuntimeException("Report not found"));

        if (report.getStatus() != ExpenseReport.Status.PENDING) {
            throw new RuntimeException("Only pending reports can be reviewed.");
        }

        Employee auditor = employeeRepository.findById(auditorId)
                .orElseThrow(() -> new RuntimeException("Auditor not found"));

        if (!"AUDITOR".equalsIgnoreCase(auditor.getRole())) {
            throw new RuntimeException("Only users with role AUDITOR can review reports.");
        }

        if (report.getCreatedBy().getId().equals(auditor.getId())) {
            throw new RuntimeException("You cannot review your own expense report.");
        }

        report.setStatus(newStatus);
        report.setReviewedBy(auditor);

        return expenseReportRepository.save(report);
    }

    // Utility method to get logged-in auditor
    private Employee getLoggedInAuditor() {
        String email = ((User) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal()).getUsername();

        Employee employee = employeeRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Logged-in user not found"));
        System.out.println("I was here"+employee.getEmail());
        if (!"AUDITOR".equalsIgnoreCase(employee.getRole())) {
            throw new RuntimeException("Access denied: You must be an auditor.");
        }

        return employee;
    }
}
 