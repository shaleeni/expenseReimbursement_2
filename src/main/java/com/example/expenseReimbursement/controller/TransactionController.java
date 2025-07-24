package com.example.expenseReimbursement.controller;

import com.example.expenseReimbursement.dto.TransactionRequestDTO;
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

    @PostMapping
    public ResponseEntity<TransactionResponseDTO> createTransaction(@RequestBody TransactionRequestDTO dto) {
        TransactionResponseDTO responseDTO = transactionService.createTransaction(dto);
        return ResponseEntity.ok(responseDTO);
    }
}
