package com.example.project1.Service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.project1.Model.Transaction;
import com.example.project1.Model.User;
import com.example.project1.Repository.TransactionRepository;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private UserService userService;

    public List<Transaction> getAllTransactions() {
        List<Transaction> transactions = transactionRepository.findAll();
        transactions.forEach(transaction -> {
            if (transaction.getSender() != null) {
                transaction.getSender().setPassword(null);
            }
            if (transaction.getReceiver() != null) {
                transaction.getReceiver().setPassword(null);
            }
        });
        return transactions;
    }

    public Transaction getTransactionById(Long id) {
        List<Transaction> transactions = transactionRepository.getTransactionById(id);
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
        return transactions.stream().findFirst().orElse(null);
    }

    public Transaction createTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    public void deleteTransaction(Long id) {
        transactionRepository.deleteById(id);
    }

    public List<Transaction> getTransactionByUserId(Long userId) {
        return transactionRepository.findTransactionsByUserId(userId);
    }

    public void transferFund(Long senderId, Long receiverId, BigDecimal amount) {
        User sender = userService.getUserById(senderId);
        User receiver = userService.getUserById(receiverId);

        if (sender.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Saldo tidak cukup");
        }
        Transaction transaction = new Transaction(amount, sender, receiver, null);
        createTransaction(transaction);

        BigDecimal newSenderBalance = sender.getBalance().subtract(amount);
        userService.updateBalance(senderId, newSenderBalance);

        BigDecimal newReceiverBalance = receiver.getBalance().add(amount);
        userService.updateBalance(receiverId, newReceiverBalance);

    }
}
