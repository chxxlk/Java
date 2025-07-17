package com.example.project1.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.project1.Model.Transaction;
import com.example.project1.Service.TransactionService;

@RestController
@RequestMapping("/api/transaction")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;

    @GetMapping
    public List<Transaction> getAllTransactions() {
        // return transactionService.getAllTransactions();
        List<Transaction> transactions = transactionService.getAllTransactions();
        transactions.forEach(transaction -> {
            if (transaction.getSender() != null) {
                transaction.getSender().setPassword(null);
                transaction.getSender().setBalance(null);
            }
            if (transaction.getReceiver() != null) {
                transaction.getReceiver().setPassword(null);
                transaction.getReceiver().setBalance(null);
            }
        });
        return transactions;
    }

    @GetMapping("/getTransactionById")
    public ResponseEntity<Transaction> getTransactionById(@RequestParam Long id) {
        Transaction transaction = transactionService.getTransactionById(id);
        return transaction != null ? ResponseEntity.ok(transaction) : ResponseEntity.notFound().build();
    }

    @GetMapping("/getTransactionByUserId")
    public List<Transaction> getTransactionByUserId(@RequestParam Long userId) {
        return transactionService.getTransactionByUserId(userId);
    }

    @PostMapping
    public Transaction createTransaction(@RequestBody Transaction transaction) {
        return transactionService.createTransaction(transaction);
    }

    @DeleteMapping("/deleteTransaction")
    public ResponseEntity<Void> deleteTransaction(@RequestParam Long id) {
        transactionService.deleteTransaction(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/transfer")
    public ResponseEntity<String> transferFund(@RequestBody Transaction.TransferRequest request,
            Long userId, Transaction transaction) {
        try {
            transactionService.transferFund(
                    request.getSenderId(),
                    request.getReceiverId(),
                    request.getAmount());
            return ResponseEntity.ok("Transfer dari user " + request.getSenderId() + " ke user "
                    + request.getReceiverId() + " dengan jumlah " + request.getAmount() + " Berhasil!");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
