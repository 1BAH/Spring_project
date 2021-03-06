package com.example.banksystem;

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

import java.util.ArrayList;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
public class BankTests {
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
    public void banks() throws Exception {
        Client client = new Client(3,"user", "sur", "add", "pass");

        ArrayList<Bank> banks = new ArrayList<>();

        BankOfficer bankOfficer = new BankOfficer("username", "120");
        Bank bank1 = new Bank("TestBank1", 1, bankOfficer);
        banks.add(bank1);
        Bank bank2 = new Bank("TestBank2", 2, bankOfficer);
        banks.add(bank2);

        Mockito.when(clientRepository.findByPassport(Mockito.any())).thenReturn(client);
        Mockito.when(bankRepository.findAll()).thenReturn(banks);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get("/banks");

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(model().attribute("banks", banks))
                .andExpect(model().attribute("user", client));
    }

    @Test
    @WithMockUser(username = "user", password = "pass")
    public void redirect() throws Exception {
        BankOfficer bankOfficer = new BankOfficer("username", "120");

        Mockito.when(bankOfficerRepository.findByUsername(Mockito.any())).thenReturn(bankOfficer);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get("/bo/banks/add/form")
                .param("name", "TestBank")
                .param("percentage", "3");

        mockMvc.perform(mockRequest)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/banks"));
    }

    @Test
    @WithMockUser(username = "user", password = "pass")
    public void nameIsEmpty() throws Exception {
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .get("/bo/banks/add/form")
                .param("percentage", "3");

        mockMvc.perform(mockRequest)
                .andExpect(status().is4xxClientError());
    }


    @Test
    @WithMockUser(username = "user", password = "pass")
    public void percentageIsEmpty() throws Exception {
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .get("/bo/banks/add/form")
                .param("name", "TestBank");

        mockMvc.perform(mockRequest)
                .andExpect(status().is4xxClientError());
    }

    @Test
    @WithMockUser(username = "user", password = "pass")
    public void bankAdd() throws Exception {
        BankOfficer bankOfficer = new BankOfficer("username", "120");

        Mockito.when(bankOfficerRepository.findByUsername(Mockito.any())).thenReturn(bankOfficer);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get("/bo/banks/add");

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(model().attribute("title", "Bank creation"))
                .andExpect(model().attribute("user", bankOfficer));
    }
}
