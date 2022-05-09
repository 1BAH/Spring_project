package com.example.banksystem.controllers;

import com.example.banksystem.models.Bank;
import com.example.banksystem.models.Client;
import com.example.banksystem.repositories.BankRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Optional;

public class BankControllerTest {
    public ArrayList<Optional<Bank>> arraylist = new ArrayList<Optional<Bank>>();
    @InjectMocks
    private BankRepository bankRepository = new BankRepository(){

        @Override
        public <S extends Bank> S save(S entity) {
            return null;
        }

        @Override
        public <S extends Bank> Iterable<S> saveAll(Iterable<S> entities) {
            return null;
        }

        @Override
        public Optional<Bank> findById(Long aLong) {
            return Optional.empty();
        }

        @Override
        public boolean existsById(Long aLong) {
            return false;
        }

        @Override
        public Iterable<Bank> findAll() {
            return null;
        }

        @Override
        public Iterable<Bank> findAllById(Iterable<Long> longs) {
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
        public void delete(Bank entity) {

        }

        @Override
        public void deleteAllById(Iterable<? extends Long> longs) {

        }

        @Override
        public void deleteAll(Iterable<? extends Bank> entities) {

        }

        @Override
        public void deleteAll() {

        }
    };
    @Autowired
    private BankController bankController = new BankController();
    @Test
    void delete() {
        bankRepository.deleteById(0L);
        ArrayList<Client> ArrayList = new ArrayList<Client>();
        Assertions.assertEquals(ArrayList, arraylist);
    }
    @Test
    void get() {
        Bank bank = new Bank();
        bank.setId(0L);
        bank.setName("name");
        bank.setPercentage((float) 0);
        Assertions.assertEquals(Optional.of(bank), bankController.banksPage(0));
    }
}
