package com.example.project2_1.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.project2_1.Model.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
        @Query("SELECT t FROM Transaction t WHERE t.id = ?1")
        List<Transaction> findByTransactionId(Long id);

        @Query("SELECT t FROM Transaction t WHERE t.sender.id = ?1 OR t.receiver.id = ?1")
        List<Transaction> findTransactionsByUserId(Long userId);

        @Query("SELECT t.sender.name AS senderName, t.receiver.name AS receiverName FROM Transaction t WHERE t.sender.id = ?1 OR t.receiver.id = ?1")
        List<Object[]> findTransactionUserNamesByUserId(Long userId);

        @Query("SELECT t FROM Transaction t WHERE t.sender.id = ?1")
        List<Transaction> findBySender(Long senderId);

        @Query("SELECT t FROM Transaction t WHERE t.receiver.id = ?1")
        List<Transaction> findByReceiver(Long receiverId);

        // @Query("SELECT t FROM Transaction t JOIN t.sender s JOIN t.receiver r " +
        // "WHERE s.id = ?1 OR r.id = ?2")
        // @Query("SELECT u.name FROM Transaction t JOIN t.receiver u WHERE
        // t.receiver.id = ?1 LIMIT 1", nativeQuery = true)
        @Query(value = "SELECT u.name AS receiverName " +
                        "FROM transactions t " +
                        "JOIN users u ON t.receiver = u.id " +
                        "WHERE t.receiver = :receiverId " +
                        "LIMIT 1", nativeQuery = true)
        String findReceiverNameByReceiverId(Long receiverId);

        // Atau jika menggunakan username sebagai PK:
        // @Query("SELECT u.name FROM Transaction t JOIN t.sender u WHERE t.sender.id =
        // ?1")
        @Query(value = "SELECT u.name AS senderName " +
                        "FROM transactions t " +
                        "JOIN users u ON t.sender = u.id " +
                        "WHERE t.sender = :senderId " +
                        "LIMIT 1", nativeQuery = true)
        String findSenderNameBySenderId(Long senderId);
}
