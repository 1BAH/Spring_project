package com.example.banksystem.controllers;

import org.junit.jupiter.api.Assertions;
import com.example.banksystem.models.Account;
import com.example.banksystem.repositories.AccountRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import java.util.List;
import java.util.Optional;

public class AccountControllerTest {
    @InjectMocks
    private AccountRepository accountRepository = new AccountRepository() {
        @Override
        public <T> Optional<T> get(long id) {
            return Optional.empty();
        }

        @Override
        public Account update(Account account) {
            return null;
        }

        @Override
        public <S extends Account> S save(S entity) {
            return null;
        }

        @Override
        public <S extends Account> Iterable<S> saveAll(Iterable<S> entities) {
            return null;
        }

        @Override
        public Optional<Account> findById(Long aLong) {
            return Optional.empty();
        }

        @Override
        public boolean existsById(Long aLong) {
            return false;
        }

        @Override
        public List<Account> findAll() {
            return null;
        }

        @Override
        public Iterable<Account> findAllById(Iterable<Long> longs) {
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
        public void delete(Account entity) {

        }

        @Override
        public void deleteAllById(Iterable<? extends Long> longs) {

        }

        @Override
        public void deleteAll(Iterable<? extends Account> entities) {

        }

        @Override
        public void deleteAll() {

        }
    };

    @Test
    void get(){
        Account account = new Account();
        account.setAmount((float) 0);
        account.setType("username");
        account.setId(0L);
        Assertions.assertEquals(Optional.of(account), accountRepository.get(0));
    }

    @Test
    void update() {
        Account account = new Account();
        account.setAmount((float) 0);
        account.setType("username");
        account.setId(0L);
        Assertions.assertEquals(account, accountRepository.update(account));
    }

}
