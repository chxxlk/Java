package com.example.project2_1.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.project2_1.Model.Transaction;
import com.example.project2_1.Model.User;
import com.example.project2_1.Repository.TransactionRepository;
import com.example.project2_1.Repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TransactionRepository transactionRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User getUserByName(String name) {
        return userRepository.findByName(name).orElse(null);
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    public User createUser(User user) {
        return userRepository.saveAndFlush(user);
    }

    public User updateUser(Long id, User userDetails) {
        User user = userRepository.findById(id).orElse(null);

        if (user != null) {
            user.setName(userDetails.getName());
            user.setEmail(userDetails.getEmail());
            user.setPassword(userDetails.getPassword());
            return userRepository.save(user);
        }
        return null;
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public List<Transaction> getSentTransactions(User user) {
        return transactionRepository.findBySender(user.getId());
    }

    public List<Transaction> getReceivedTransactions(User user) {
        return transactionRepository.findByReceiver(user.getId());
    }

    @Transactional
    public void deleteUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow();

        // Nullify sender references
        for (Transaction transaction : getSentTransactions(user)) {
            if (transaction.getSender().equals(user)) {
                transaction.setSender(null);
            }
        }

        // Nullify receiver references
        for (Transaction transaction : getReceivedTransactions(user)) {
            if (transaction.getReceiver().equals(user)) {
                transaction.setReceiver(null);
            }
        }

        userRepository.delete(user);
    }

    public User updateBalance(Long id, BigDecimal newBalance) {
        User user = userRepository.findById(id).orElseThrow();
        user.setBalance(newBalance);
        return userRepository.save(user);
    }

    public Optional<Object[]> findBalanceById(Long id) {
        return userRepository.findNameAndBalanceById(id).stream().findFirst();
    }
}
