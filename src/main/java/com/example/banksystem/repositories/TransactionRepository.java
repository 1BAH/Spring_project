package com.example.banksystem.repositories;

import com.example.banksystem.models.Transaction;
import org.springframework.data.repository.CrudRepository;

/**
 * Transaction repository extends CrudRepository
 */
public interface TransactionRepository extends CrudRepository<Transaction, Long> {
}
