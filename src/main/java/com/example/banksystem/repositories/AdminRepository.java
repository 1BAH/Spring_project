package com.example.banksystem.repositories;

import com.example.banksystem.models.Admin;
import org.springframework.data.repository.CrudRepository;

/**
 * Admin repository extends CrudRepository
 */
public interface AdminRepository extends CrudRepository<Admin, Long> {
    Admin findByName(String string);
}
