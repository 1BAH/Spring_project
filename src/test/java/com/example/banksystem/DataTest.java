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
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class DataTest {
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
    public void data() throws Exception{
        Client client = new Client(3,"user", "sur", "add", "pass");
        Bank bank = new Bank(1, "bank", 10);

        Account account1 = new Account(1, new BigDecimal(1000), "Account1", bank, client);
        client.addAccounts(account1);
        Account account2 = new Account(2, new BigDecimal(2000), "Account2", bank, client);
        client.addAccounts(account2);
        Account account3 = new Account(2, new BigDecimal(3000), "Account2", bank, client);
        client.addAccounts(account3);

        account2.setAlert((byte) 2);
        account1.setAlert((byte) 1);

        String response = "{";

        response += "\"" + account1.getId()
                + "\":\"Account @" + account1.getId()
                + " was filled up <a href=\\\"/transaction/" + account1.getLastTransactionId()
                + "\\\" class=\\\"nav-link fst-italic text-warning\\\">See more</a>\","
                + "\"" + account2.getId() + "\":\"Credit account @" + account2.getId() + " fee\"}";

        Mockito.when(clientRepository.findByPassport(Mockito.any())).thenReturn(client);
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .get("/data");

        MvcResult result = mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andReturn();

        Assert.assertEquals(response, result.getResponse().getContentAsString());
    }

    @Test
    @WithMockUser(username = "user", password = "pass")
    public void close() throws Exception {
        Bank bank = new Bank(1, "bank", 10);
        Client client = new Client(3,"user", "sur", "add", "pass");
        Account account = new Account(2, new BigDecimal(1000), "Credit", bank, client);
        account.setAlert((byte) 2);
        bank.addAccounts(account);
        client.addAccounts(account);

        Mockito.when(accountRepository.findById(Mockito.any())).thenReturn(java.util.Optional.of(account));

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .get("/close/2");

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk());

        Assert.assertEquals(0, account.getAlert());
    }
}
