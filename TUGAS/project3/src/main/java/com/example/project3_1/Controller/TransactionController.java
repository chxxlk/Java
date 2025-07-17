package com.example.project3_1.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.project3_1.Model.Transaction;
import com.example.project3_1.Service.TransactionService;

@RestController
@RequestMapping("/transactions")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;

    @PostMapping("/transfer")
    public ResponseEntity<String> transfer(@RequestBody Transaction.TransferRequest request, String username) {
        // Transaction transaction = new Transaction();
        try {
            // System.out.println("Transfer Amount: " + request.getAmount());
            // System.out.println("Transfer Amount: " + transaction.getAmount());
            transactionService.transfer(
                    request.getSenderName(),
                    request.getReceiverName(),
                    request.getAmount());
            return ResponseEntity.ok("Transfer successful");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public @ResponseBody List<Transaction> getAllTransactions(Transaction transaction) {
        return transactionService.getAllTransactions();
    }

    @GetMapping("/{transactionNumber}")
    public @ResponseBody List<Transaction> findTransactionsByTranferNumber(@PathVariable String transactionNumber) {
        return transactionService.findTransactionsByTranferNumber(transactionNumber);
    }
}
