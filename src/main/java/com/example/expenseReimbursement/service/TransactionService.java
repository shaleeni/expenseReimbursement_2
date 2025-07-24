package com.example.expenseReimbursement.service;
import com.example.expenseReimbursement.dto.TransactionResponseDTO;
import com.example.expenseReimbursement.entity.Employee;
import com.example.expenseReimbursement.entity.ExpenseReport;
import com.example.expenseReimbursement.entity.Transaction;
import com.example.expenseReimbursement.repository.EmployeeRepository;
import com.example.expenseReimbursement.repository.ExpenseReportRepository;
import com.example.expenseReimbursement.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

@Service

public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private ExpenseReportRepository expenseReportRepository;
    @Autowired
    private EmployeeRepository employeeRepository;

    // Make the whole transaction creation and report status update atomic

    @Transactional

    public TransactionResponseDTO createTransaction(Long reportId) {

        Employee employee = getLoggedInFinance();
        Optional<ExpenseReport> reportOpt = expenseReportRepository.findById(reportId);

        if (reportOpt.isEmpty()) {
            throw new RuntimeException("Invalid report ID");
        }

        ExpenseReport report = reportOpt.get();

        // Only allow payment if report status is APPROVED
        if (report.getStatus() != ExpenseReport.Status.APPROVED) {
            throw new RuntimeException("Report must be approved before it can be paid.");
        }

        // Create and save the transaction
        Transaction transaction = new Transaction();
        transaction.setReport(report);
        transaction.setPaidBy(employee);
        transaction.setAmount(report.getTotalAmount());
        transaction.setFromAccount("BHARTI_AIRTEL_ACCOUNT");
        transaction.setToAccount(employee.getAccNo());
        transactionRepository.save(transaction);
        // Update report status to PAID and persist immediately
        report.setStatus(ExpenseReport.Status.PAID);
        expenseReportRepository.save(report);
        return mapToResponseDTO(transaction);
    }

    private TransactionResponseDTO mapToResponseDTO(Transaction transaction) {
        TransactionResponseDTO dto = new TransactionResponseDTO();
        dto.setTransactionId(transaction.getTransactionId());
        dto.setReportId(transaction.getReport().getReportId());
        dto.setPaidByName(transaction.getPaidBy().getName());
        dto.setAmount(transaction.getAmount());
        dto.setFromAccount(transaction.getFromAccount());
        dto.setToAccount(transaction.getToAccount());
        return dto;
    }

    public Map<String, Object> getFormattedTransactionReport(Long transactionId) {
        getLoggedInFinance();
        Transaction txn = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));
        Employee employee = txn.getPaidBy();
        ExpenseReport report = txn.getReport();
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("transactionId", txn.getTransactionId());
        response.put("reportId", report.getReportId());
        response.put("amount", txn.getAmount());
        response.put("fromAccount", "BHARTI AIRTEL");
        response.put("toAccount", employee.getAccNo());
        response.put("status", report.getStatus().name());  // dynamic status from DB
        response.put("approvedBy", report.getReviewedBy() != null ? report.getReviewedBy().getName() : "");
        response.put("ifscCode", employee.getIfscCode());
        response.put("phoneNumber", employee.getPhoneNumber());
        return response;
    }

    private Employee getLoggedInFinance() {
        String email = ((User) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal()).getUsername();

        Employee employee = employeeRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Logged-in user not found"));
        System.out.println("I was here"+employee.getEmail());
        if (!"FINANCE".equalsIgnoreCase(employee.getRole())) {
            throw new RuntimeException("Access denied: You must be an finance person.");
        }
        return employee;
    }
}

 