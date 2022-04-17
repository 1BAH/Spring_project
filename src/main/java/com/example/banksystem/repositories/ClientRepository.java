package com.example.banksystem.repositories;

import com.example.banksystem.models.Client;
import org.springframework.data.repository.CrudRepository;

public interface ClientRepository extends CrudRepository<Client, Long> {
    Client findByName(String name);
}
