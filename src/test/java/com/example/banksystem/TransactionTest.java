package com.example.banksystem;

import com.example.banksystem.models.Account;
import com.example.banksystem.models.Bank;
import com.example.banksystem.models.Client;
import com.example.banksystem.models.Transaction;
import com.example.banksystem.repositories.AccountRepository;
import com.example.banksystem.repositories.BankRepository;
import com.example.banksystem.repositories.ClientRepository;
import com.example.banksystem.repositories.TransactionRepository;
import com.example.banksystem.securityconfig.CustomAuthenticationEntryPoint;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.util.ArrayList;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
public class TransactionTest {
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
    CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    @Test
    @WithMockUser(username = "user", password = "pass")
    public void transactions() throws Exception {
        Client client = new Client(3,"user", "sur", "add", "pass");
        Bank bank = new Bank(1, "bank", 10);
        Account account1 = new Account(1, new BigDecimal(1000), "Account1", bank, client);
        Account account2 = new Account(2, new BigDecimal(2000), "Account2", bank, client);
        bank.addAccounts(account1);
        bank.addAccounts(account2);
        client.addAccounts(account1);
        client.addAccounts(account2);

        ArrayList<Transaction> transactions = new ArrayList<>();
        Transaction transaction1 = new Transaction(1L, account1, account2, new BigDecimal(1000));
        transactions.add(transaction1);
        Transaction transaction2 = new Transaction(2L, account1, account2, new BigDecimal(2000));
        transactions.add(transaction2);

        Mockito.when(clientRepository.findByName(Mockito.any())).thenReturn(client);
        Mockito.when(transactionRepository.findAll()).thenReturn(transactions);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get("/transactions");

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(model().attribute("transactions", transactions))
                .andExpect(model().attribute("user", client));
    }

    @Test
    @WithMockUser(username = "user", password = "pass")
    public void redirect() throws Exception {
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .get("/transactions/make")
                .param("accountFrom", "TestTransactionFrom")
                .param("accountTo", "TestTransactionTo")
                .param("amount", "100");

        mockMvc.perform(mockRequest)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/transactions"));
    }

    @Test
    @WithMockUser(username = "user", password = "pass")
    public void accountFromIsEmpty() throws Exception {
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .get("/transactions/make/step-2/{fromId}/form")
                .param("accountTo", "TestTransaction")
                .param("amount", "3");

        mockMvc.perform(mockRequest)
                .andExpect(status().is4xxClientError());
    }

    @Test
    @WithMockUser(username = "user", password = "pass")
    public void accountToIsEmpty() throws Exception {
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .get("/transactions/make/step-2/{toId}/form")
                .param("accountFrom", "TestTransaction")
                .param("amount", "3");

        mockMvc.perform(mockRequest)
                .andExpect(status().is4xxClientError());
    }

    @Test
    @WithMockUser(username = "user", password = "pass")
    public void amountIsEmpty() throws Exception {
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .get("/transactions/make/{fromId}/{toId}/{amount}/{withCommision}")
                .param("accountTo", "TestTransaction1")
                .param("accountFrom", "TestTransaction2");

        mockMvc.perform(mockRequest)
                .andExpect(status().is4xxClientError());
    }

    @Test
    @WithMockUser(username = "user", password = "pass")
    public void transactionMake() throws Exception {
        Client client = new Client(3,"user", "sur", "add", "pass");

        Mockito.when(clientRepository.findByName(Mockito.any())).thenReturn(client);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get("/transactions/make");

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(model().attribute("title", "Transaction making"))
                .andExpect(model().attribute("user", client));
    }

}
