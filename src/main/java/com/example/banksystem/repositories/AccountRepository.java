package com.example.banksystem.repositories;

import com.example.banksystem.models.Account;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AccountRepository extends CrudRepository<Account, Long> {
}
