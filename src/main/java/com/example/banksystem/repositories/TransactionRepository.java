package com.example.banksystem.repositories;

import com.example.banksystem.models.Account;
import org.springframework.data.repository.CrudRepository;

public interface TransactionRepository extends CrudRepository<Account, Long> {
}
