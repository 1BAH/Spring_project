package com.example.banksystem;

import com.example.banksystem.models.Account;
import com.example.banksystem.models.Bank;
import com.example.banksystem.models.BankOfficer;
import com.example.banksystem.models.Client;
import com.example.banksystem.repositories.*;
import com.example.banksystem.securityconfig.CustomAuthenticationEntryPoint;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.util.Optional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
public class WithdrawTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    @MockBean
    ClientRepository clientRepository;

    @MockBean
    AccountRepository accountRepository;

    @MockBean
    BankRepository bankRepository;

    @MockBean
    TransactionRepository transactionRepository;

    @MockBean
    BankOfficerRepository bankOfficerRepository;

    @MockBean
    AdminRepository adminRepository;

    @MockBean
    BankOfficerPrototypeRepository bankOfficerPrototypeRepository;

    @MockBean
    CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    @Test
    @WithMockUser(username = "user", password = "pass")
    public void withdraw() throws Exception {
        BankOfficer bankOfficer = new BankOfficer("username", "120");
        Bank bank = new Bank(1, "bank", 10, bankOfficer);
        Client client = new Client(3,"user", "sur", "add", "pass");
        Account account = new Account(2, new BigDecimal(1000), "Credit", bank, client);
        bank.addAccounts(account);
        client.addAccounts(account);

        Mockito.when(clientRepository.findByPassport(Mockito.any())).thenReturn(client);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .get("/withdraw");

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(model().attribute("title", "Withdraw money"))
                .andExpect(model().attribute("user", client))
                .andExpect(model().attribute("accounts", client.getAccounts()));
    }

    @Test
    @WithMockUser(username = "user", password = "pass")
    public void choose() throws Exception {
        BankOfficer bankOfficer = new BankOfficer("username", "120");
        Bank bank = new Bank(1, "bank", 10, bankOfficer);
        Client client = new Client(3,"user", "sur", "add", "pass");
        Account account = new Account(2, new BigDecimal(1000), "Credit", bank, client);
        bank.addAccounts(account);
        client.addAccounts(account);

        Mockito.when(clientRepository.findByPassport(Mockito.any())).thenReturn(client);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .get("/withdraw/choose")
                .param("accountId", "2");

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(model().attribute("title", "Withdraw money"))
                .andExpect(model().attribute("user", client))
                .andExpect(model().attribute("choice", "2"));
    }

    @Test
    @WithMockUser(username = "user", password = "pass")
    public void withdrawOperation() throws Exception {
        BankOfficer bankOfficer = new BankOfficer("username", "120");
        Bank bank = new Bank(1, "bank", 10, bankOfficer);
        Client client = new Client(3,"user", "sur", "add", "pass");
        Account account = new Account(2, new BigDecimal(1111), "Credit", bank, client);
        bank.addAccounts(account);
        client.addAccounts(account);

        Mockito.when(accountRepository.findById(Mockito.any())).thenReturn(Optional.of(account));
        Mockito.when(clientRepository.findByPassport(Mockito.any())).thenReturn(client);


        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .get("/withdraw/2")
                .param("amount", "111");

        mockMvc.perform(mockRequest)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/accounts"));

        Assert.assertTrue(new BigDecimal(1000).compareTo(account.getAmount()) == 0);
    }

    @Test
    @WithMockUser(username = "user", password = "pass")
    public void chooseIdIsEmpty() throws Exception {
        BankOfficer bankOfficer = new BankOfficer("username", "120");
        Bank bank = new Bank(1, "bank", 10, bankOfficer);
        Client client = new Client(3,"user", "sur", "add", "pass");
        Account account = new Account(2, new BigDecimal(1000), "Credit", bank, client);
        bank.addAccounts(account);
        client.addAccounts(account);

        Mockito.when(clientRepository.findByPassport(Mockito.any())).thenReturn(client);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .get("/withdraw/choose");

        mockMvc.perform(mockRequest)
                .andExpect(status().is4xxClientError());
    }

    @Test
    @WithMockUser(username = "user", password = "pass")
    public void withdrawAmountIsEmpty() throws Exception {
        BankOfficer bankOfficer = new BankOfficer("username", "120");
        Bank bank = new Bank(1, "bank", 10, bankOfficer);
        Client client = new Client(3,"user", "sur", "add", "pass");
        Account account = new Account(2, new BigDecimal(1000), "Credit", bank, client);
        bank.addAccounts(account);
        client.addAccounts(account);

        Mockito.when(accountRepository.findById(Mockito.any())).thenReturn(Optional.of(account));
        Mockito.when(clientRepository.findByPassport(Mockito.any())).thenReturn(client);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .get("/withdraw/2");

        mockMvc.perform(mockRequest)
                .andExpect(status().is4xxClientError());
    }

    @Test
    @WithMockUser(username = "user", password = "pass")
    public void withdrawCreditBelowZero() throws Exception {
        BankOfficer bankOfficer = new BankOfficer("username", "120");
        Bank bank = new Bank(1, "bank", 10, bankOfficer);
        Client client = new Client(3,"user", "sur", "add", "pass");
        Account account = new Account(2, new BigDecimal(0), "Credit", bank, client);
        bank.addAccounts(account);
        client.addAccounts(account);

        Mockito.when(accountRepository.findById(Mockito.any())).thenReturn(Optional.of(account));
        Mockito.when(clientRepository.findByPassport(Mockito.any())).thenReturn(client);


        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .get("/withdraw/2")
                .param("amount", "111");

        mockMvc.perform(mockRequest)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/accounts"));

        Assert.assertTrue(new BigDecimal(-111).compareTo(account.getAmount()) == 0);
    }

    @Test
    @WithMockUser(username = "user", password = "pass")
    public void withdrawDebitBelowZero() throws Exception {
        BankOfficer bankOfficer = new BankOfficer("username", "120");
        Bank bank = new Bank(1, "bank", 10, bankOfficer);
        Client client = new Client(3,"user", "sur", "add", "pass");
        Account account = new Account(2, new BigDecimal(0), "Debit", bank, client);
        bank.addAccounts(account);
        client.addAccounts(account);

        Mockito.when(accountRepository.findById(Mockito.any())).thenReturn(Optional.of(account));
        Mockito.when(clientRepository.findByPassport(Mockito.any())).thenReturn(client);


        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .get("/withdraw/2")
                .param("amount", "111");

        mockMvc.perform(mockRequest)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/withdraw/withdraw-error"));

        Assert.assertTrue(new BigDecimal(0).compareTo(account.getAmount()) == 0);
    }

    @Test
    @WithMockUser(username = "user", password = "pass")
    public void withdrawError() throws Exception {
        BankOfficer bankOfficer = new BankOfficer("username", "120");
        Bank bank = new Bank(1, "bank", 10, bankOfficer);
        Client client = new Client(3,"user", "sur", "add", "pass");
        Account account = new Account(2, new BigDecimal(0), "Debit", bank, client);
        bank.addAccounts(account);
        client.addAccounts(account);

        Mockito.when(clientRepository.findByPassport(Mockito.any())).thenReturn(client);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .get("/withdraw/withdraw-error");

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(model().attribute("title", "ERROR"))
                .andExpect(model().attribute("user", client))
                .andExpect(model().attribute("accounts", client.getAccounts()));
    }
}
