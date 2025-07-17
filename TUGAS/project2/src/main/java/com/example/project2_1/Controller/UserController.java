package com.example.project2_1.Controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.project2_1.Model.User;
import com.example.project2_1.Service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/getUserById")
    public ResponseEntity<User> getUserById(@RequestParam Long id) {
        User user = userService.getUserById(id);
        return user != null ? ResponseEntity.ok(user) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User userDetails) {
        User updateUser = userService.updateUser(id, userDetails);
        return updateUser != null ? ResponseEntity.ok(updateUser) : ResponseEntity.notFound().build();
    }

    @GetMapping("/balance")
    public ResponseEntity<Optional<Object[]>> findBalanceById(@RequestParam Long id) {
        Optional<Object[]> user = userService.findBalanceById(id);
        return user != null ? ResponseEntity.ok(user) : ResponseEntity.notFound().build();
    }

    @PutMapping("/balance")
    public ResponseEntity<User> updateBalance(@RequestParam Long id, @RequestParam BigDecimal balance) {
        User user = userService.updateBalance(id, balance);
        return user != null ? ResponseEntity.ok(user) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/deleteById")
    public ResponseEntity<Void> deleteUserById(@RequestParam Long id) {
        userService.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }
}
