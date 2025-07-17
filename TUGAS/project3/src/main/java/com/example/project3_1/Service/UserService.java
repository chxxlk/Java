package com.example.project3_1.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import com.example.project3_1.Model.User;
import com.example.project3_1.Repository.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User addUser(User user) {
        User exitingUsername = userRepository.findByUsername(user.getUsername()).orElse(null);

        if (exitingUsername != null) {
            throw new RuntimeException("Username '" + user.getUsername() + "' sudah digunakan!");
        }

        try {
            return userRepository.insert(user);
        } catch (DuplicateKeyException e) {
            throw new RuntimeException("Username '" + user.getUsername() + "' sudah digunakan!");
        }
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    public User updateUser(String username, User updatedUser) {
        User user = userRepository.findByUsername(username).orElse(null);

        if (user != null) {
            user.setName(updatedUser.getName());
            user.setEmail(updatedUser.getEmail());
            user.setPassword(updatedUser.getPassword());
            return userRepository.save(user);
        }
        return null;
    }

    public void deleteUser(String username) {
        userRepository.deleteByUsername(username);
    }

    public User updateBalance(String username, double balance) {
        User user = userRepository.findByUsername(username).orElse(null);
        user.setBalance(balance);
        return userRepository.save(user);
    }
}
