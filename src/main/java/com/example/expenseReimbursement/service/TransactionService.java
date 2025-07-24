package com.example.expenseReimbursement.service;

import com.example.expenseReimbursement.dto.TransactionRequestDTO;
import com.example.expenseReimbursement.dto.TransactionResponseDTO;
import com.example.expenseReimbursement.entity.Employee;
import com.example.expenseReimbursement.entity.ExpenseReport;
import com.example.expenseReimbursement.entity.Transaction;
import com.example.expenseReimbursement.repository.EmployeeRepository;
import com.example.expenseReimbursement.repository.ExpenseReportRepository;
import com.example.expenseReimbursement.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private ExpenseReportRepository expenseReportRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    public TransactionResponseDTO createTransaction(TransactionRequestDTO dto) {
        Optional<Employee> employeeOpt = employeeRepository.findById(dto.getPaidById());
        Optional<ExpenseReport> reportOpt = expenseReportRepository.findById(dto.getReportId());

        if (employeeOpt.isEmpty() || reportOpt.isEmpty()) {
            throw new RuntimeException("Invalid employee or report ID");
        }

        Employee employee = employeeOpt.get();
        ExpenseReport report = reportOpt.get();

        // Build transaction with account data pulled from employee only
        Transaction transaction = new Transaction();
        transaction.setReport(report);
        transaction.setPaidBy(employee);
        transaction.setAmount(dto.getAmount());
        transaction.setPaidAt(dto.getPaidAt());

        transaction.setFromAccount(employee.getAccNo()); // From employee
        transaction.setToAccount("ORG_CORP_ACCOUNT");     // You can replace with config/env variable

        transactionRepository.save(transaction);

        return mapToResponseDTO(transaction);
    }

    private TransactionResponseDTO mapToResponseDTO(Transaction transaction) {
        TransactionResponseDTO dto = new TransactionResponseDTO();
        dto.setTransactionId(transaction.getTransactionId());
        dto.setReportId(transaction.getReport().getReportId());
        dto.setPaidByName(transaction.getPaidBy().getName());
        dto.setAmount(transaction.getAmount());
        dto.setPaidAt(transaction.getPaidAt());
        dto.setFromAccount(transaction.getFromAccount());
        dto.setToAccount(transaction.getToAccount());
        return dto;
    }
}
