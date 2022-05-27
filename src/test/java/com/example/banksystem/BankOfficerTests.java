package com.example.banksystem;

import com.example.banksystem.models.Account;
import com.example.banksystem.models.Bank;
import com.example.banksystem.models.BankOfficer;
import com.example.banksystem.models.Client;
import com.example.banksystem.repositories.*;
import com.example.banksystem.securityconfig.CustomAuthenticationEntryPoint;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.util.Optional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class BankOfficerTests {
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
    public void info() throws Exception {
        BankOfficer bankOfficer = new BankOfficer("username", "120");
        Bank bank = new Bank(1, "bank", 1, bankOfficer);

        Mockito.when(bankOfficerRepository.findByUsername(Mockito.any())).thenReturn(bankOfficer);
        Mockito.when(bankRepository.findById(Mockito.any())).thenReturn(Optional.of(bank));


        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get("/bo/bank-info/1");

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(model().attribute("title", "Bank @1"))
                .andExpect(model().attribute("bank", bank));
    }

    @Test
    @WithMockUser(username = "user", password = "pass")
    public void changeInfo() throws Exception {
        BankOfficer bankOfficer = new BankOfficer("username", "120");
        Bank bank = new Bank(1, "bank", 1, bankOfficer);

        Mockito.when(bankOfficerRepository.findByUsername(Mockito.any())).thenReturn(bankOfficer);
        Mockito.when(bankRepository.findById(Mockito.any())).thenReturn(Optional.of(bank));


        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .get("/bo/bank-change-info/form/1")
                .param("name", "m")
                .param("percentage", "5");

        mockMvc.perform(mockRequest)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/bo/bank-info/1"));
    }

    @Test
    @WithMockUser(username = "user", password = "pass")
    public void changeInfoNameEmpty() throws Exception {
        BankOfficer bankOfficer = new BankOfficer("username", "120");
        Bank bank = new Bank(1, "bank", 1, bankOfficer);

        Mockito.when(bankOfficerRepository.findByUsername(Mockito.any())).thenReturn(bankOfficer);
        Mockito.when(bankRepository.findById(Mockito.any())).thenReturn(Optional.of(bank));


        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .get("/bo/bank-change-info/form/1")
                .param("percentage", "5");

        mockMvc.perform(mockRequest)
                .andExpect(status().is4xxClientError());
    }

    @Test
    @WithMockUser(username = "user", password = "pass")
    public void changeInfoPercentageEmpty() throws Exception {
        BankOfficer bankOfficer = new BankOfficer("username", "120");
        Bank bank = new Bank(1, "bank", 1, bankOfficer);

        Mockito.when(bankOfficerRepository.findByUsername(Mockito.any())).thenReturn(bankOfficer);
        Mockito.when(bankRepository.findById(Mockito.any())).thenReturn(Optional.of(bank));


        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .get("/bo/bank-change-info/form/1")
                .param("name", "m");

        mockMvc.perform(mockRequest)
                .andExpect(status().is4xxClientError());
    }

    @Test
    @WithMockUser(username = "user", password = "pass")
    public void changeInfo2() throws Exception {
        BankOfficer bankOfficer = new BankOfficer("username", "120");
        Bank bank = new Bank(1, "bank", 1, bankOfficer);

        Mockito.when(bankOfficerRepository.findByUsername(Mockito.any())).thenReturn(bankOfficer);
        Mockito.when(bankRepository.findById(Mockito.any())).thenReturn(Optional.of(bank));


        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get("/bo/bank-change-info/1");

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(model().attribute("title", "Change information"))
                .andExpect(model().attribute("bank", bank));
    }

    @Test
    @WithMockUser(username = "user", password = "pass")
    public void changeInfoForbidden() throws Exception {
        BankOfficer bankOfficer1 = new BankOfficer("e", "y");
        BankOfficer bankOfficer = new BankOfficer("username", "120");
        Bank bank = new Bank(1, "bank", 1, bankOfficer);

        Mockito.when(bankOfficerRepository.findByUsername(Mockito.any())).thenReturn(bankOfficer1);
        Mockito.when(bankRepository.findById(Mockito.any())).thenReturn(Optional.of(bank));


        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get("/bo/bank-change-info/1");

        mockMvc.perform(mockRequest)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/bo/forbidden"));
    }

    @Test
    @WithMockUser(username = "user", password = "pass")
    public void forbidden() throws Exception {
        BankOfficer bankOfficer = new BankOfficer("username", "120");
        Bank bank = new Bank(1, "bank", 1, bankOfficer);

        Mockito.when(bankOfficerRepository.findByUsername(Mockito.any())).thenReturn(bankOfficer);
        Mockito.when(bankRepository.findById(Mockito.any())).thenReturn(Optional.of(bank));


        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get("/bo/forbidden");

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(model().attribute("title", "ERROR"));
    }

    @Test
    @WithMockUser(username = "user", password = "pass")
    public void deleteAccount() throws Exception {
        BankOfficer bankOfficer = new BankOfficer("username", "120");
        Bank bank = new Bank(1, "bank", 1, bankOfficer);
        Client client = new Client(3,"user", "sur", "add", "pass");
        Account account = new Account(1, new BigDecimal(1000), "Account1", bank, client);

        Mockito.when(bankOfficerRepository.findByUsername(Mockito.any())).thenReturn(bankOfficer);
        Mockito.when(bankRepository.findById(Mockito.any())).thenReturn(Optional.of(bank));
        Mockito.when(accountRepository.findById(Mockito.any())).thenReturn(Optional.of(account));


        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get("/bo/account-delete/1");

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(model().attribute("title", "Respond to request"))
                .andExpect(model().attribute("acc", account));
    }

    @Test
    @WithMockUser(username = "user", password = "pass")
    public void deleteAccountProcessTrue() throws Exception {
        BankOfficer bankOfficer = new BankOfficer("username", "120");
        Bank bank = new Bank(1, "bank", 1, bankOfficer);
        Client client = new Client(3,"user", "sur", "add", "pass");
        Account account = new Account(1, new BigDecimal(1000), "Account1", bank, client);

        Mockito.when(bankOfficerRepository.findByUsername(Mockito.any())).thenReturn(bankOfficer);
        Mockito.when(bankRepository.findById(Mockito.any())).thenReturn(Optional.of(bank));
        Mockito.when(accountRepository.findById(Mockito.any())).thenReturn(Optional.of(account));


        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .get("/bo/account-delete/1/process")
                .param("response", "true");

        mockMvc.perform(mockRequest)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/bo/account-delete-success/1"));
    }

    @Test
    @WithMockUser(username = "user", password = "pass")
    public void deleteAccountProcessFalse() throws Exception {
        BankOfficer bankOfficer = new BankOfficer("username", "120");
        Bank bank = new Bank(1, "bank", 1, bankOfficer);
        Client client = new Client(3,"user", "sur", "add", "pass");
        Account account = new Account(1, new BigDecimal(1000), "Account1", bank, client);

        Mockito.when(bankOfficerRepository.findByUsername(Mockito.any())).thenReturn(bankOfficer);
        Mockito.when(bankRepository.findById(Mockito.any())).thenReturn(Optional.of(bank));
        Mockito.when(accountRepository.findById(Mockito.any())).thenReturn(Optional.of(account));


        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .get("/bo/account-delete/1/process")
                .param("response", "false");

        mockMvc.perform(mockRequest)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/bo/account-delete-reject/1"));

        Assert.assertNull(account.getBankOfficer());
        Assert.assertEquals(account.getAlert(), (byte) 3);
    }

    @Test
    @WithMockUser(username = "user", password = "pass")
    public void success() throws Exception {
        BankOfficer bankOfficer = new BankOfficer("username", "120");
        Bank bank = new Bank(1, "bank", 1, bankOfficer);
        Client client = new Client(3,"user", "sur", "add", "pass");
        Account account = new Account(1, new BigDecimal(1000), "Account1", bank, client);

        Mockito.when(bankOfficerRepository.findByUsername(Mockito.any())).thenReturn(bankOfficer);
        Mockito.when(bankRepository.findById(Mockito.any())).thenReturn(Optional.of(bank));
        Mockito.when(accountRepository.findById(Mockito.any())).thenReturn(Optional.of(account));


        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .get("/bo/account-delete-success/1");

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(model().attribute("id", 1L))
                .andExpect(model().attribute("title", "Success"));
    }

    @Test
    @WithMockUser(username = "user", password = "pass")
    public void reject() throws Exception {
        BankOfficer bankOfficer = new BankOfficer("username", "120");
        Bank bank = new Bank(1, "bank", 1, bankOfficer);
        Client client = new Client(3,"user", "sur", "add", "pass");
        Account account = new Account(1, new BigDecimal(1000), "Account1", bank, client);

        Mockito.when(bankOfficerRepository.findByUsername(Mockito.any())).thenReturn(bankOfficer);
        Mockito.when(bankRepository.findById(Mockito.any())).thenReturn(Optional.of(bank));
        Mockito.when(accountRepository.findById(Mockito.any())).thenReturn(Optional.of(account));


        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .get("/bo/account-delete-reject/1");

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(model().attribute("id", 1L))
                .andExpect(model().attribute("title", "Reject"));
    }
}
