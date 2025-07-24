package com.example.expenseReimbursement.controller;

import com.example.expenseReimbursement.dto.TransactionResponseDTO;
import com.example.expenseReimbursement.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @GetMapping("/{id}/report")
    public ResponseEntity<TransactionResponseDTO> createTransaction(@PathVariable Long id) {
        TransactionResponseDTO responseDTO = transactionService.createTransaction(id);
        return ResponseEntity.ok(responseDTO);
    }

//    // ✅ Endpoint to get full transaction report
    @GetMapping("/fetch/{transactionId}")
    public ResponseEntity<?> getFormattedTransactionReport(@PathVariable Long transactionId) {
        return ResponseEntity.ok(transactionService.getFormattedTransactionReport(transactionId));
    }
}