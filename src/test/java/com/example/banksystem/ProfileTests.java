package com.example.banksystem;

import com.example.banksystem.models.*;
import com.example.banksystem.repositories.*;
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

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class ProfileTests {
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
    AdminRepository adminRepository;

    @MockBean
    BankOfficerRepository bankOfficerRepository;

    @MockBean
    BankOfficerPrototypeRepository bankOfficerPrototypeRepository;

    @MockBean
    TransactionRepository transactionRepository;

    @MockBean
    CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    @Test
    @WithMockUser(username = "user", password = "pass")
    public void registration() throws  Exception {
        Client client = new Client(3, "user", "sur", "add", "pass");
        BankOfficer bankOfficer = new BankOfficer("username", "120");
        Bank bank = new Bank("bank", 1, bankOfficer);

        Account account1 = new Account(1, new BigDecimal(1000), "Account1", bank, client);
        client.addAccounts(account1);
        Account account2 = new Account(2, new BigDecimal(2000), "Account2", bank, client);
        client.addAccounts(account2);

        ArrayList<Transaction> transactions = new ArrayList<>();

        Transaction transaction1 = new Transaction(1L, account1, account2, new BigDecimal(100));
        transactions.add(transaction1);
        Transaction transaction2 = new Transaction(2L, account2, account1, new BigDecimal(1000));
        transactions.add(transaction2);

        Mockito.when(clientRepository.findByPassport(Mockito.any())).thenReturn(client);
        Mockito.when(transactionRepository.findAll()).thenReturn(transactions);


        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .get("/profile");

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(model().attribute("title", "Profile"))
                .andExpect(model().attribute("accounts", client.getAccounts()))
                .andExpect(model().attribute("user", client))
                .andExpect(model().attribute("transactions", transactions));
    }
}
