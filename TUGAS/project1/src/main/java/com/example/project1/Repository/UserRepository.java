package com.example.project1.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.project1.Model.User;
// import org.springframework.stereotype.Repository;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findByName(String name);

    @Query("SELECT u.name, u.balance FROM User u WHERE u.id = ?1")
    List<Object[]> findNameAndBalanceById(Long id);
}
