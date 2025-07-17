package com.example.project2_1.Service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.project2_1.Model.Transaction;
import com.example.project2_1.Model.User;
import com.example.project2_1.Repository.TransactionRepository;

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
                transaction.getSender().setBalance(null);
                transaction.getSender().setEmail(null);
            }
            if (transaction.getReceiver() != null) {
                transaction.getReceiver().setPassword(null);
                transaction.getReceiver().setBalance(null);
                transaction.getReceiver().setEmail(null);
            }
        });
        return transactions;
    }

    public Transaction getTransactionById(Long id) {
        List<Transaction> transactions = transactionRepository.findByTransactionId(id);
        transactions.forEach(transaction -> {
            if (transaction.getSender() != null) {
                transaction.getSender().setPassword(null);
                transaction.getSender().setBalance(null);
                transaction.getSender().setEmail(null);
            }
            if (transaction.getReceiver() != null) {
                transaction.getReceiver().setPassword(null);
                transaction.getReceiver().setBalance(null);
                transaction.getReceiver().setEmail(null);
            }
        });
        return transactions.stream().findFirst().orElse(null);
    }

    public List<Transaction> getTransactionByUserId(Long userId) {
        List<Transaction> transactions = transactionRepository.findTransactionsByUserId(userId);
        transactions.forEach(transaction -> {
            if (transaction.getSender() != null) {
                transaction.getSender().setPassword(null);
                transaction.getSender().setBalance(null);
                transaction.getSender().setEmail(null);
            }
            if (transaction.getReceiver() != null) {
                transaction.getReceiver().setPassword(null);
                transaction.getReceiver().setBalance(null);
                transaction.getReceiver().setEmail(null);
            }
        });
        return transactionRepository.findTransactionsByUserId(userId);
    }

    public Transaction createTransaction(Transaction transaction) {
        return transactionRepository.saveAndFlush(transaction);
    }

    public void deleteTransaction(Long id) {
        transactionRepository.deleteById(id);
    }

    public List<Object[]> findTransactionUserNamesByUserId(Long userId) {
        return transactionRepository.findTransactionUserNamesByUserId(userId);
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

    public String findNameBySenderId(Long senderId) {
        return transactionRepository.findSenderNameBySenderId(senderId);
    }

    public String findNameByReceiverId(Long receiverId) {
        return transactionRepository.findReceiverNameByReceiverId(receiverId);
    }
}
