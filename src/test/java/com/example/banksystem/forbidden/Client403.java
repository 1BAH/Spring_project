package com.example.banksystem.forbidden;

import com.example.banksystem.models.Admin;
import com.example.banksystem.models.Bank;
import com.example.banksystem.models.BankOfficer;
import com.example.banksystem.models.Client;
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

import java.util.Optional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
public class Client403 {
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
    public void clBanksAdd() throws Exception {
        com.example.banksystem.models.Client client = new com.example.banksystem.models.Client(3,"user", "sur", "add", "pass");

        Mockito.when(clientRepository.findByPassport(Mockito.any())).thenReturn(client);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get("/bo/banks/add");

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(model().attribute("title", "403 FORBIDDEN"));
    }

    @Test
    @WithMockUser(username = "user", password = "pass")
    public void clBanksAddForm() throws Exception {
        com.example.banksystem.models.Client client = new com.example.banksystem.models.Client(3,"user", "sur", "add", "pass");

        Mockito.when(clientRepository.findByPassport(Mockito.any())).thenReturn(client);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .get("/bo/banks/add/form")
                .param("name", "name")
                .param("percentage", "1");

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(model().attribute("title", "403 FORBIDDEN"));
    }

    @Test
    @WithMockUser(username = "user", password = "pass")
    public void clBankInfo() throws Exception {
        com.example.banksystem.models.Client client = new com.example.banksystem.models.Client(3,"user", "sur", "add", "pass");
        BankOfficer bankOfficer = new BankOfficer("username", "120");
        Bank bank = new Bank("bank", 1, bankOfficer);

        Mockito.when(clientRepository.findByPassport(Mockito.any())).thenReturn(client);
        Mockito.when(bankRepository.findById(Mockito.any())).thenReturn(Optional.of(bank));


        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get("/bo/bank-info/3");

        mockMvc.perform(mockRequest)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/bo/forbidden"));
    }

    @Test
    @WithMockUser(username = "user", password = "pass")
    public void clBankChangeInfo() throws Exception {
        com.example.banksystem.models.Client client = new com.example.banksystem.models.Client(3,"user", "sur", "add", "pass");
        BankOfficer bankOfficer = new BankOfficer("username", "120");
        Bank bank = new Bank("bank", 1, bankOfficer);

        Mockito.when(clientRepository.findByPassport(Mockito.any())).thenReturn(client);
        Mockito.when(bankRepository.findById(Mockito.any())).thenReturn(Optional.of(bank));

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get("/bo/bank-change-info/3");

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(model().attribute("title", "403 FORBIDDEN"));
    }

    @Test
    @WithMockUser(username = "user", password = "pass")
    public void clBankChangeInfoFrom() throws Exception {
        com.example.banksystem.models.Client client = new com.example.banksystem.models.Client(3,"user", "sur", "add", "pass");
        BankOfficer bankOfficer = new BankOfficer("username", "120");
        Bank bank = new Bank("bank", 1, bankOfficer);

        Mockito.when(clientRepository.findByPassport(Mockito.any())).thenReturn(client);
        Mockito.when(bankRepository.findById(Mockito.any())).thenReturn(Optional.of(bank));

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .get("/bo/bank-change-info/form/3")
                .param("name", "name")
                .param("percentage", "1");

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(model().attribute("title", "403 FORBIDDEN"));
    }
}
