package com.example.banksystem.repositories;

import com.example.banksystem.models.BankOfficer;
import org.springframework.data.repository.CrudRepository;

/**
 * Bank officer repository extends CrudRepository
 */
public interface BankOfficerRepository extends CrudRepository<BankOfficer, Long> {
    BankOfficer findByUsername(String username);
}
