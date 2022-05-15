package com.example.banksystem.repositories;

import com.example.banksystem.models.Client;
import org.springframework.data.repository.CrudRepository;

/**
 * Client repository extends CrudRepository
 */
public interface ClientRepository extends CrudRepository<Client, Long> {
    /**
     * A query to find a client by given passport
     * @param passport
     * @return client
     */
    Client findByPassport(String passport);
}
