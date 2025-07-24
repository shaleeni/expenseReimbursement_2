package com.example.expenseReimbursement.service;
 
import com.example.expenseReimbursement.entity.Employee;
import com.example.expenseReimbursement.entity.ExpenseReport;
import com.example.expenseReimbursement.repository.EmployeeRepository;
import com.example.expenseReimbursement.repository.ExpenseReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
 
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
        return expenseReportRepository.findByStatus(status);
    }
 
    // For backward compatibility (e.g., existing controller code)
    public List<ExpenseReport> getPendingReports() {
        return getReportsByStatus(ExpenseReport.Status.PENDING);
    }
 
    // Get specific report only if it's in pending status
    public Optional<ExpenseReport> getReportForAudit(Long reportId) {
        Optional<ExpenseReport> report = expenseReportRepository.findById(reportId);
        return report.filter(r -> r.getStatus() == ExpenseReport.Status.PENDING);
    }
 
    // Approve a report
    public ExpenseReport approveReport(Long reportId, Long auditorId) {
        return reviewReport(reportId, auditorId, ExpenseReport.Status.APPROVED);
    }
 
    // Decline a report
    public ExpenseReport declineReport(Long reportId, Long auditorId) {
        return reviewReport(reportId, auditorId, ExpenseReport.Status.DECLINED);
    }
 
    // Shared logic for approving/declining
    private ExpenseReport reviewReport(Long reportId, Long auditorId, ExpenseReport.Status newStatus) {
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
}
 