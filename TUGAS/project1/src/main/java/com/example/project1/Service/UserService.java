package com.example.project1.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.project1.Model.User;
import com.example.project1.Repository.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

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
        return userRepository.save(user);
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

    public User updateBalance(Long userId, BigDecimal newBalance) {
        User user = userRepository.findById(userId).orElseThrow();
        user.setBalance(newBalance);
        return userRepository.save(user);
    }

    public Optional<Object[]> findBalanceById(Long id) {
        return userRepository.findNameAndBalanceById(id).stream().findFirst();
    }

    public User updateBalance(User user, BigDecimal balance) {
        user.setBalance(balance);
        return userRepository.save(user);
    }
}
