package com.example.project2_1.Controller;

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

import com.example.project2_1.Model.Transaction;
import com.example.project2_1.Service.TransactionService;

@RestController
@RequestMapping("/api/transaction")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;

    @GetMapping
    public List<Transaction> getAllTransactions() {
        return transactionService.getAllTransactions();
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

    // @PostMapping
    // public Transaction createTransaction(@RequestBody Transaction transaction) {
    // return transactionService.createTransaction(transaction);
    // }

    @DeleteMapping("/deleteTransaction")
    public ResponseEntity<Void> deleteTransaction(@RequestParam Long id) {
        transactionService.deleteTransaction(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/findTransactionUserNamesByUserId")
    public List<Object[]> findTransactionUserNamesByUserId(@RequestParam Long userId) {
        return transactionService.findTransactionUserNamesByUserId(userId);
    }

    @PostMapping("/transfer")
    public ResponseEntity<String> transferFund(@RequestBody Transaction.TransferRequest request,
            Long id, Transaction transaction) {
        try {
            transactionService.transferFund(
                    request.getSenderId(),
                    request.getReceiverId(),
                    request.getAmount());
            return ResponseEntity
                    .ok("Transfer dari user "
                            + transactionService.findNameBySenderId(request.getSenderId()) + " ke user "
                            + transactionService.findNameByReceiverId(request.getReceiverId()) + " dengan jumlah "
                            + request.getAmount() + " Berhasil!");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
