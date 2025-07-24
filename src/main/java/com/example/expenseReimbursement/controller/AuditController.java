package com.example.expenseReimbursement.controller;
 
import com.example.expenseReimbursement.entity.ExpenseReport;
import com.example.expenseReimbursement.service.AuditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
 
import java.util.List;
import java.util.Optional;
 
@RestController
@RequestMapping("/api/audit")
public class AuditController {
 
    @Autowired
    private AuditService auditService;
 
    // Get reports pending audit
    @GetMapping("/reports/pending")
    public List<ExpenseReport> getPendingReports() {
        return auditService.getPendingReports();
    }
    @GetMapping("/reports/approved")
    public List<ExpenseReport> getApprovedReports() {
        return auditService.getReportsByStatus(ExpenseReport.Status.APPROVED);
    }
 
    @GetMapping("/reports/declined")
    public List<ExpenseReport> getDeclinedReports() {
        return auditService.getReportsByStatus(ExpenseReport.Status.DECLINED);
    }
 
    // Get report details for audit
    @GetMapping("/reports/{id}")
    public Optional<ExpenseReport> getReportForAudit(@PathVariable Long id) {
        return auditService.getReportForAudit(id);
    }
 
    // Approve report (additional functionality you might need)
    @PutMapping("/reports/{id}/approve")
    public ExpenseReport approveReport(@PathVariable Long id, @RequestParam Long auditorId) {
        return auditService.approveReport(id, auditorId);
    }
 
    // Decline report (additional functionality you might need)
    @PutMapping("/reports/{id}/decline")
    public ExpenseReport declineReport(@PathVariable Long id, @RequestParam Long auditorId) {
        return auditService.declineReport(id, auditorId);
    }
}