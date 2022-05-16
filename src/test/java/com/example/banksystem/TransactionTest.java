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
import java.util.ArrayList;
import java.util.Optional;

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
    public void transactionPage() throws Exception {
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

        Mockito.when(clientRepository.findByPassport(Mockito.any())).thenReturn(client);
        Mockito.when(transactionRepository.findAll()).thenReturn(transactions);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get("/transactions");

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(model().attribute("transactions", transactions))
                .andExpect(model().attribute("user", client));
    }

    @Test
    @WithMockUser(username = "user", password = "pass")
    public void redirectToStep1() throws Exception {
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .get("/transactions/make")
                .param("accountFrom", "TestTransactionFrom")
                .param("accountTo", "TestTransactionTo")
                .param("amount", "100");

        mockMvc.perform(mockRequest)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/transactions/step-1"));
    }

    @Test
    @WithMockUser(username = "user", password = "pass")
    public void step1() throws Exception {
        Bank bank = new Bank(1, "bank", 10);
        Client client = new Client(3,"user", "sur", "add", "pass");

        ArrayList<Account> accounts = new ArrayList<>();

        Account account1 = new Account(1L, new BigDecimal(0), "Debit", bank, client);
        Account account2 = new Account(2L, new BigDecimal(1000), "Credit", bank, client);

        accounts.add(account1);
        client.addAccounts(account1);
        accounts.add(account2);
        client.addAccounts(account2);

        Mockito.when(clientRepository.findByPassport(Mockito.any())).thenReturn(client);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .get("/transactions/step-1");

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(model().attribute("title", "Step 1"))
                .andExpect(model().attribute("user", client))
                .andExpect(model().attribute("accounts", accounts));
    }

    @Test
    @WithMockUser(username = "user", password = "pass")
    public void redirectToStep2self() throws Exception {
        Bank bank = new Bank(1, "bank", 10);
        Client client = new Client(3,"user", "sur", "add", "pass");

        ArrayList<Account> accounts = new ArrayList<>();

        Account account1 = new Account(1L, new BigDecimal(0), "Debit", bank, client);
        Account account2 = new Account(2L, new BigDecimal(1000), "Credit", bank, client);

        accounts.add(account1);
        client.addAccounts(account1);
        accounts.add(account2);
        client.addAccounts(account2);

        Mockito.when(clientRepository.findByPassport(Mockito.any())).thenReturn(client);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .get("/transactions/make/step-1/form")
                .param("accountFrom", "1")
                .param("self", "true");

        mockMvc.perform(mockRequest)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/transactions/make/step-2-self/1"));
    }

    @Test
    @WithMockUser(username = "user", password = "pass")
    public void redirectToStep2() throws Exception {
        Bank bank = new Bank(1, "bank", 10);
        Client client = new Client(3,"user", "sur", "add", "pass");

        ArrayList<Account> accounts = new ArrayList<>();

        Account account1 = new Account(1L, new BigDecimal(0), "Debit", bank, client);
        Account account2 = new Account(2L, new BigDecimal(1000), "Credit", bank, client);

        accounts.add(account1);
        client.addAccounts(account1);
        accounts.add(account2);
        client.addAccounts(account2);

        Mockito.when(clientRepository.findByPassport(Mockito.any())).thenReturn(client);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .get("/transactions/make/step-1/form")
                .param("accountFrom", "2")
                .param("self", "false");

        mockMvc.perform(mockRequest)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/transactions/make/step-2/2"));
    }

    @Test
    @WithMockUser(username = "user", password = "pass")
    public void redirectToStep2SelfIsMissing() throws Exception {
        Bank bank = new Bank(1, "bank", 10);
        Client client = new Client(3,"user", "sur", "add", "pass");

        ArrayList<Account> accounts = new ArrayList<>();

        Account account1 = new Account(1L, new BigDecimal(0), "Debit", bank, client);
        Account account2 = new Account(2L, new BigDecimal(1000), "Credit", bank, client);

        accounts.add(account1);
        client.addAccounts(account1);
        accounts.add(account2);
        client.addAccounts(account2);

        Mockito.when(clientRepository.findByPassport(Mockito.any())).thenReturn(client);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .get("/transactions/make/step-1/form")
                .param("accountFrom", "1");

        mockMvc.perform(mockRequest)
                .andExpect(status().is4xxClientError());
    }

    @Test
    @WithMockUser(username = "user", password = "pass")
    public void redirectToStep2AccountFromIsMissing() throws Exception {
        Bank bank = new Bank(1, "bank", 10);
        Client client = new Client(3,"user", "sur", "add", "pass");

        ArrayList<Account> accounts = new ArrayList<>();

        Account account1 = new Account(1L, new BigDecimal(0), "Debit", bank, client);
        Account account2 = new Account(2L, new BigDecimal(1000), "Credit", bank, client);

        accounts.add(account1);
        client.addAccounts(account1);
        accounts.add(account2);
        client.addAccounts(account2);

        Mockito.when(clientRepository.findByPassport(Mockito.any())).thenReturn(client);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .get("/transactions/make/step-1/form")
                .param("self", "true");

        mockMvc.perform(mockRequest)
                .andExpect(status().is4xxClientError());
    }

    @Test
    @WithMockUser(username = "user", password = "pass")
    public void step2self() throws Exception {
        Bank bank = new Bank(1, "bank", 10);
        Client client = new Client(3,"user", "sur", "add", "pass");

        ArrayList<Account> accounts = new ArrayList<>();

        Account account1 = new Account(1L, new BigDecimal(0), "Debit", bank, client);
        Account account2 = new Account(2L, new BigDecimal(1000), "Credit", bank, client);

        accounts.add(account1);
        client.addAccounts(account1);
        accounts.add(account2);
        client.addAccounts(account2);

        Mockito.when(clientRepository.findByPassport(Mockito.any())).thenReturn(client);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .get("/transactions/make/step-2-self/1");

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(model().attribute("title", "Step 2"))
                .andExpect(model().attribute("user", client))
                .andExpect(model().attribute("accounts", accounts))
                .andExpect(model().attribute("chosenAcc", 1L));
    }

    @Test
    @WithMockUser(username = "user", password = "pass")
    public void step2() throws Exception {
        Bank bank = new Bank(1, "bank", 10);
        Client client = new Client(3,"user", "sur", "add", "pass");

        ArrayList<Account> accounts = new ArrayList<>();

        Account account1 = new Account(1L, new BigDecimal(0), "Debit", bank, client);
        Account account2 = new Account(2L, new BigDecimal(1000), "Credit", bank, client);

        accounts.add(account1);
        client.addAccounts(account1);
        accounts.add(account2);
        client.addAccounts(account2);

        Mockito.when(clientRepository.findByPassport(Mockito.any())).thenReturn(client);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .get("/transactions/make/step-2/1");

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(model().attribute("title", "Step 2"))
                .andExpect(model().attribute("user", client))
                .andExpect(model().attribute("chosenAcc", 1L));
    }

    @Test
    @WithMockUser(username = "user", password = "pass")
    public void step2selfForm() throws Exception {
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .get("/transactions/make/step-2-self/1/form")
                .param("accountTo", "3");

        mockMvc.perform(mockRequest)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/transactions/make/step-3/1/3"));
    }

    @Test
    @WithMockUser(username = "user", password = "pass")
    public void step2Form() throws Exception {
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .get("/transactions/make/step-2/1/form")
                .param("accountTo", "3");

        mockMvc.perform(mockRequest)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/transactions/make/step-3/1/3"));
    }

    @Test
    @WithMockUser(username = "user", password = "pass")
    public void redirectToStep3selfAccountToIsMissing() throws Exception {
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .get("/transactions/make/step-2-self/1/form");

        mockMvc.perform(mockRequest)
                .andExpect(status().is4xxClientError());
    }

    @Test
    @WithMockUser(username = "user", password = "pass")
    public void redirectToStep3AccountToIsMissing() throws Exception {
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .get("/transactions/make/step-2/1/form");

        mockMvc.perform(mockRequest)
                .andExpect(status().is4xxClientError());
    }

    @Test
    @WithMockUser(username = "user", password = "pass")
    public void step3() throws Exception {
        Bank bank = new Bank(1, "bank", 10);
        Client client = new Client(3,"user", "sur", "add", "pass");

        ArrayList<Account> accounts = new ArrayList<>();

        Account account1 = new Account(1L, new BigDecimal(0), "Debit", bank, client);
        Account account2 = new Account(2L, new BigDecimal(1000), "Credit", bank, client);

        accounts.add(account1);
        client.addAccounts(account1);
        accounts.add(account2);
        client.addAccounts(account2);

        Mockito.when(clientRepository.findByPassport(Mockito.any())).thenReturn(client);
        Mockito.when(accountRepository.findById(1L)).thenReturn(Optional.of(account1));
        Mockito.when(accountRepository.findById(2L)).thenReturn(Optional.of(account2));


        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .get("/transactions/make/step-3/1/2");

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(model().attribute("title", "Step 3"))
                .andExpect(model().attribute("user", client))
                .andExpect(model().attribute("accounts", accounts))
                .andExpect(model().attribute("fromAcc", account1))
                .andExpect(model().attribute("toAcc", account2));
    }

    @Test
    @WithMockUser(username = "user", password = "pass")
    public void step4WithoutCommision() throws Exception {
        Bank bank = new Bank(1, "bank", 10);
        Client client = new Client(3,"user", "sur", "add", "pass");

        ArrayList<Account> accounts = new ArrayList<>();

        Account account1 = new Account(1L, new BigDecimal(0), "Debit", bank, client);
        Account account2 = new Account(2L, new BigDecimal(1000), "Credit", bank, client);

        accounts.add(account1);
        client.addAccounts(account1);
        accounts.add(account2);
        client.addAccounts(account2);

        Mockito.when(clientRepository.findByPassport(Mockito.any())).thenReturn(client);
        Mockito.when(accountRepository.findById(1L)).thenReturn(Optional.of(account1));
        Mockito.when(accountRepository.findById(2L)).thenReturn(Optional.of(account2));

        BigDecimal amount = new BigDecimal(111);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .get("/transactions/make/step-4/1/2/form")
                .param("amount", "111");

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(model().attribute("title", "Step 4"))
                .andExpect(model().attribute("user", client))
                .andExpect(model().attribute("fromAcc", account1))
                .andExpect(model().attribute("toAcc", account2))
                .andExpect(model().attribute("amount", amount))
                .andExpect(model().attribute("colored", true))
                .andExpect(model().attribute("commision", 0F))
                .andExpect(model().attribute("realAmount", amount.multiply(new BigDecimal(1 + 0 / 100)).setScale(2, BigDecimal.ROUND_HALF_DOWN)));
    }

    @Test
    @WithMockUser(username = "user", password = "pass")
    public void step4WithCommision() throws Exception {
        Bank bank1 = new Bank(1, "bank1", 10);
        Bank bank2 = new Bank(2, "bank2", 20);
        Client client = new Client(3,"user", "sur", "add", "pass");

        ArrayList<Account> accounts = new ArrayList<>();

        Account account1 = new Account(1L, new BigDecimal(1000), "Debit", bank1, client);
        Account account2 = new Account(2L, new BigDecimal(1000), "Credit", bank2, client);

        accounts.add(account1);
        client.addAccounts(account1);
        accounts.add(account2);
        client.addAccounts(account2);

        Mockito.when(clientRepository.findByPassport(Mockito.any())).thenReturn(client);
        Mockito.when(accountRepository.findById(1L)).thenReturn(Optional.of(account1));
        Mockito.when(accountRepository.findById(2L)).thenReturn(Optional.of(account2));

        BigDecimal amount = new BigDecimal(100);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .get("/transactions/make/step-4/1/2/form")
                .param("amount", "100");

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(model().attribute("title", "Step 4"))
                .andExpect(model().attribute("user", client))
                .andExpect(model().attribute("fromAcc", account1))
                .andExpect(model().attribute("toAcc", account2))
                .andExpect(model().attribute("amount", amount))
                .andExpect(model().attribute("colored", false))
                .andExpect(model().attribute("commision", 10F))
                .andExpect(model().attribute("realAmount", amount.multiply(new BigDecimal(1.1)).setScale(2, BigDecimal.ROUND_HALF_DOWN)));
    }

    @Test
    @WithMockUser(username = "user", password = "pass")
    public void transactionWithoutCommision() throws Exception {
        Client client = new Client(3, "user", "sur", "add", "pass");
        Bank bank = new Bank(1, "bank", 10);

        Account account1 = new Account(1L, new BigDecimal(1000), "Credit", bank, client);
        Account account2 = new Account(2L, new BigDecimal(1000), "Credit", bank, client);

        Mockito.when(accountRepository.findById((long) 1)).thenReturn(Optional.of(account1));
        Mockito.when(accountRepository.findById((long) 2)).thenReturn(Optional.of(account2));
        bank.addAccounts(account1);
        bank.addAccounts(account2);
        client.addAccounts(account1);
        client.addAccounts(account2);

        Transaction transaction = new Transaction(1L, account1, account2, new BigDecimal(100));
        transactionRepository.save(transaction);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .get("/transactions/make/1/2/100/true");

        mockMvc.perform(mockRequest)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/transactions"));

        Assert.assertTrue(new BigDecimal(900).compareTo(account1.getAmount()) == 0);
        Assert.assertTrue(new BigDecimal(1100).compareTo(account2.getAmount()) == 0);
    }

    @Test
    @WithMockUser(username = "user", password = "pass")
    public void transactionWithCommision() throws Exception {
        Client client = new Client(3, "user", "sur", "add", "pass");
        Bank bank = new Bank(1, "bank", 10);

        Account account1 = new Account(1L, new BigDecimal(1000), "Credit", bank, client);
        Account account2 = new Account(2L, new BigDecimal(1000), "Credit", bank, client);

        Mockito.when(accountRepository.findById((long) 1)).thenReturn(Optional.of(account1));
        Mockito.when(accountRepository.findById((long) 2)).thenReturn(Optional.of(account2));
        bank.addAccounts(account1);
        bank.addAccounts(account2);
        client.addAccounts(account1);
        client.addAccounts(account2);

        Transaction transaction = new Transaction(1L, account1, account2, new BigDecimal(100));
        transactionRepository.save(transaction);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .get("/transactions/make/1/2/100/false");

        mockMvc.perform(mockRequest)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/transactions"));

        Assert.assertTrue(new BigDecimal(890).compareTo(account1.getAmount().setScale(2, BigDecimal.ROUND_HALF_UP)) == 0);
        Assert.assertTrue(new BigDecimal(1100).compareTo(account2.getAmount()) == 0);
    }

    @Test
    @WithMockUser(username = "user", password = "pass")
    public void transactionError() throws Exception {
        Client client = new Client(3, "user", "sur", "add", "pass");
        Bank bank = new Bank(1, "bank", 10);

        Account account1 = new Account(1L, new BigDecimal(0), "Debit", bank, client);
        Account account2 = new Account(2L, new BigDecimal(1000), "Credit", bank, client);

        Mockito.when(accountRepository.findById((long) 1)).thenReturn(Optional.of(account1));
        Mockito.when(accountRepository.findById((long) 2)).thenReturn(Optional.of(account2));
        bank.addAccounts(account1);
        bank.addAccounts(account2);
        client.addAccounts(account1);
        client.addAccounts(account2);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .get("/transactions/make/1/2/100/true");

        mockMvc.perform(mockRequest)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/transactions/transaction-error"));

        Assert.assertTrue(new BigDecimal(0).compareTo(account1.getAmount()) == 0);
        Assert.assertTrue(new BigDecimal(1000).compareTo(account2.getAmount()) == 0);
    }

    @Test
    @WithMockUser(username = "user", password = "pass")
    public void errorRedirect() throws Exception {
        Bank bank = new Bank(1, "bank", 10);
        Client client = new Client(3,"user", "sur", "add", "pass");

        ArrayList<Account> accounts = new ArrayList<>();

        Account account1 = new Account(1L, new BigDecimal(0), "Debit", bank, client);
        Account account2 = new Account(2L, new BigDecimal(1000), "Credit", bank, client);

        accounts.add(account1);
        client.addAccounts(account1);
        accounts.add(account2);
        client.addAccounts(account2);

        Mockito.when(clientRepository.findByPassport(Mockito.any())).thenReturn(client);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get("/transactions/transaction-error");

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(model().attribute("title", "ERROR"))
                .andExpect(model().attribute("user", client))
                .andExpect(model().attribute("accounts", accounts));
    }

}