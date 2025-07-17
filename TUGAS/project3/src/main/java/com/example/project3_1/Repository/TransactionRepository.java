package com.example.project3_1.Repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.project3_1.Model.Transaction;

public interface TransactionRepository extends MongoRepository<Transaction, String> {
    public List<Transaction> findByTransactionNumber(String transactionNumber);
}
