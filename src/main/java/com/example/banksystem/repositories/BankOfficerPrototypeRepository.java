package com.example.banksystem.repositories;

import com.example.banksystem.models.BankOfficerPrototype;
import org.springframework.data.repository.CrudRepository;

/**
 * Bank officer prototype repository extends CrudRepository
 */
public interface BankOfficerPrototypeRepository extends CrudRepository<BankOfficerPrototype, Long> {
    BankOfficerPrototype findByUsername(String username);
}
