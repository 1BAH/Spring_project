package com.example.banksystem.models.Repositories;

import com.example.banksystem.models.Bank;
import org.springframework.data.repository.CrudRepository;

public interface BankRepository extends CrudRepository<Bank, Long> {
}
