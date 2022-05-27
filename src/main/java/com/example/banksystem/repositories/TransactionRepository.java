package com.example.banksystem.repositories;

import com.example.banksystem.models.Account;
import com.example.banksystem.models.Transaction;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Transaction repository extends CrudRepository
 */
public interface TransactionRepository extends CrudRepository<Transaction, Long> {
    List<Transaction> findAllByAccountFrom(Account account);

    List<Transaction> findAllByAccountTo(Account account);

}
