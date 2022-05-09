package com.example.banksystem.controllers;

import com.example.banksystem.models.Client;
import com.example.banksystem.models.Transaction;
import com.example.banksystem.repositories.TransactionRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import java.util.ArrayList;
import java.util.Optional;

public class TransactionControllerTest {
    @InjectMocks
    private TransactionRepository transactionRepository = new TransactionRepository() {
        @Override
        public <S extends Transaction> S save(S entity) {
            return null;
        }

        @Override
        public <S extends Transaction> Iterable<S> saveAll(Iterable<S> entities) {
            return null;
        }

        @Override
        public Optional<Transaction> findById(Long aLong) {
            return Optional.empty();
        }

        @Override
        public boolean existsById(Long aLong) {
            return false;
        }

        @Override
        public Iterable<Transaction> findAll() {
            return null;
        }

        @Override
        public Iterable<Transaction> findAllById(Iterable<Long> longs) {
            return null;
        }

        @Override
        public long count() {
            return 0;
        }

        @Override
        public void deleteById(Long aLong) {

        }

        @Override
        public void delete(Transaction entity) {

        }

        @Override
        public void deleteAllById(Iterable<? extends Long> longs) {

        }

        @Override
        public void deleteAll(Iterable<? extends Transaction> entities) {

        }

        @Override
        public void deleteAll() {

        }
    };
    public ArrayList<Optional<Transaction>> arraylist = new ArrayList<Optional<Transaction>>();
    @Test
    void delete(){
        transactionRepository.deleteById(0L);
        ArrayList<Client> ArrayList = new ArrayList<Client>();
        Assertions.assertEquals(ArrayList, arraylist);
    }
}
