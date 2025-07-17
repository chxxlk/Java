package com.example.project3_1.Service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.project3_1.Model.Transaction;
import com.example.project3_1.Model.User;
import com.example.project3_1.Repository.TransactionRepository;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private UserService userService;

    public Transaction createTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    public void transfer(String senderName, String receiverName, double amount) {
        User sender = userService.findByUsername(senderName);
        User receiver = userService.findByUsername(receiverName);
        String transactionNumber = "TR" + (transactionRepository.count() + 1);
        LocalDateTime timestamp = LocalDateTime.now();

        if (sender.getBalance() <= 0) {
            throw new IllegalArgumentException("Insufficient balance.");
        }

        Transaction transaction = new Transaction(transactionNumber, amount, sender, receiver, timestamp);
        createTransaction(transaction);

        double newSenderBalance = sender.getBalance() - amount;
        userService.updateBalance(senderName, newSenderBalance);

        double newReceiverBalance = receiver.getBalance() + amount;
        userService.updateBalance(receiverName, newReceiverBalance);
    }

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    public List<Transaction> findTransactionsByTranferNumber(String transactionNumber) {
        return transactionRepository.findByTransactionNumber(transactionNumber);
    }
}
