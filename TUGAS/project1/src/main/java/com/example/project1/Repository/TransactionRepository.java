package com.example.project1.Repository;

import com.example.project1.Model.Transaction;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    @Query("select t from Transaction t where t.sender.id = ?1 or t.receiver.id = ?1")
    List<Transaction> findTransactionsByUserId(Long userId);

    List<Transaction> getTransactionById(Long id);
}
