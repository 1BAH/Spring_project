package com.example.banksystem.repositories;

import com.example.banksystem.models.Account;
import org.springframework.data.repository.CrudRepository;

/**
 * Account repository extends CrudRepository
 */
public interface AccountRepository extends CrudRepository<Account, Long> {
}
