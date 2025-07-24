package com.example.expenseReimbursement.controller;

import com.example.expenseReimbursement.entity.Employee;
import com.example.expenseReimbursement.entity.ExpenseItem;
import com.example.expenseReimbursement.entity.ExpenseReport;
import com.example.expenseReimbursement.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;


    // Create expense report

    @PostMapping("/reports")
    public ExpenseReport createReport(@RequestParam Long employeeId) {
        return employeeService.createReport(employeeId);
    }

    // Get employee's reports
    @GetMapping("/reports")
    public List<ExpenseReport> myReports(@RequestParam Long employeeId) {
        return employeeService.getReportsByEmployee(employeeId);
    }

    // Get specific report details
    @GetMapping("/reports/{id}")
    public Optional<ExpenseReport> getReportById(@PathVariable Long id) {
        return employeeService.getReportById(id);
    }

    // Add expense item to report
    @PostMapping("/reports/{id}/items")
    public ExpenseItem addItemToReport(@PathVariable Long id, @RequestBody ExpenseItem item) {
        return employeeService.addItemToReport(id, item);
    }

    // Submit report for audit
    @PutMapping("/reports/{id}/submit")
    public ExpenseReport submitReport(@PathVariable Long id) {
        return employeeService.submitReport(id);
    }
}
