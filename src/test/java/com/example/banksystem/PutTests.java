package com.example.banksystem;

import com.example.banksystem.models.Account;
import com.example.banksystem.models.Bank;
import com.example.banksystem.models.Client;
import com.example.banksystem.repositories.AccountRepository;
import com.example.banksystem.repositories.BankRepository;
import com.example.banksystem.repositories.ClientRepository;
import com.example.banksystem.repositories.TransactionRepository;
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

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
public class PutTests {
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
    public void testName() throws Exception {
        Bank bank = new Bank(1, "bank", 10);
        Client client = new Client(3,"user", "sur", "add", "pass");
        Account account = new Account(2, new BigDecimal(1000), "Credit", bank, client);
        bank.addAccounts(account);
        client.addAccounts(account);

        Mockito.when(clientRepository.findByName(Mockito.any())).thenReturn(client);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .get("/put");

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(model().attribute("title", "Put money"))
                .andExpect(model().attribute("user", client))
                .andExpect(model().attribute("accounts", client.getAccounts()));
    }

    @Test
    @WithMockUser(username = "user", password = "pass")
    public void choose() throws Exception {
        Bank bank = new Bank(1, "bank", 10);
        Client client = new Client(3,"user", "sur", "add", "pass");
        Account account = new Account(2, new BigDecimal(1000), "Credit", bank, client);
        bank.addAccounts(account);
        client.addAccounts(account);

        Mockito.when(clientRepository.findByName(Mockito.any())).thenReturn(client);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .get("/put/choose")
                .param("accountId", "2");

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(model().attribute("title", "Put money"))
                .andExpect(model().attribute("user", client))
                .andExpect(model().attribute("choice", "2"));
    }

    @Test
    @WithMockUser(username = "user", password = "pass")
    public void chooseIdIsEmpty() throws Exception {
        Bank bank = new Bank(1, "bank", 10);
        Client client = new Client(3,"user", "sur", "add", "pass");
        Account account = new Account(2, new BigDecimal(1000), "Credit", bank, client);
        bank.addAccounts(account);
        client.addAccounts(account);

        Mockito.when(clientRepository.findByName(Mockito.any())).thenReturn(client);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .get("/put/choose");

        mockMvc.perform(mockRequest)
                .andExpect(status().is4xxClientError());
    }

    @Test
    @WithMockUser(username = "user", password = "pass")
    public void put() throws Exception {
        Bank bank = new Bank(1, "bank", 10);
        Client client = new Client(3,"user", "sur", "add", "pass");
        Account account = new Account(2, new BigDecimal(1000), "Credit", bank, client);
        bank.addAccounts(account);
        client.addAccounts(account);

        Mockito.when(accountRepository.findById(Mockito.any())).thenReturn(java.util.Optional.of(account));

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .get("/put/2")
                .param("amount", "111");

        mockMvc.perform(mockRequest)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/accounts"));

        Assert.assertTrue(new BigDecimal(1111).compareTo(account.getAmount()) == 0);
    }

    @Test
    @WithMockUser(username = "user", password = "pass")
    public void putAmountIsEmpty() throws Exception {
        Bank bank = new Bank(1, "bank", 10);
        Client client = new Client(3,"user", "sur", "add", "pass");
        Account account = new Account(2, new BigDecimal(1000), "Credit", bank, client);
        bank.addAccounts(account);
        client.addAccounts(account);

        Mockito.when(accountRepository.findById(Mockito.any())).thenReturn(java.util.Optional.of(account));

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .get("/put/2");

        mockMvc.perform(mockRequest)
                .andExpect(status().is4xxClientError());
    }
}
