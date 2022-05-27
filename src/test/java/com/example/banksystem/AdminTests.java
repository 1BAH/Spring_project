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

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
public class AdminTests {
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
    @WithMockUser(username = "user", password = "pass", roles = {"ADMIN"})
    public void add() throws Exception {
        Admin admin = new Admin();
        BankOfficerPrototype bankOfficer = new BankOfficerPrototype("username", "120", admin);

        Mockito.when(bankOfficerPrototypeRepository.findByUsername(Mockito.any())).thenReturn(bankOfficer);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .get("/adm/add-bank-officer/a");

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(model().attribute("title", "Response"))
                .andExpect(model().attribute("bankOfficer", bankOfficer));
    }

    @Test
    @WithMockUser(username = "user", password = "pass", roles = {"ADMIN"})
    public void addProcessError() throws Exception {
        Admin admin = new Admin();
        BankOfficerPrototype bankOfficer = new BankOfficerPrototype("username", "120", admin);

        Mockito.when(bankOfficerPrototypeRepository.findByUsername(Mockito.any())).thenReturn(bankOfficer);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .get("/adm/add-bank-officer/a/process");

        mockMvc.perform(mockRequest)
                .andExpect(status().is4xxClientError());
    }

    @Test
    @WithMockUser(username = "user", password = "pass", roles = {"ADMIN"})
    public void addProcessFalse() throws Exception {
        Admin admin = new Admin();
        BankOfficerPrototype bankOfficer = new BankOfficerPrototype("username", "120", admin);

        Mockito.when(bankOfficerPrototypeRepository.findByUsername(Mockito.any())).thenReturn(bankOfficer);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .get("/adm/add-bank-officer/a/process")
                .param("response", "false");

        mockMvc.perform(mockRequest)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/adm/add-bank-officer-reject/a"));
    }

    @Test
    @WithMockUser(username = "user", password = "pass", roles = {"ADMIN"})
    public void addProcessTrue() throws Exception {
        Admin admin = new Admin();
        BankOfficerPrototype bankOfficer = new BankOfficerPrototype("username", "120", admin);

        Mockito.when(bankOfficerPrototypeRepository.findByUsername(Mockito.any())).thenReturn(bankOfficer);

        Mockito.when(bankOfficerRepository.save(Mockito.any(BankOfficer.class)))
                .thenAnswer(invocationOnMock -> {
                    if (invocationOnMock.getArguments()[0] instanceof BankOfficer){
                        ((BankOfficer) invocationOnMock.getArguments()[0]).setId(1L);
                    }
                    return null;
                });

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .get("/adm/add-bank-officer/a/process")
                .param("response", "true");

        mockMvc.perform(mockRequest)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/adm/add-bank-officer-success/1"));
    }

    @Test
    @WithMockUser(username = "user", password = "pass", roles = {"ADMIN"})
    public void accept() throws Exception {
        Admin admin = new Admin();
        BankOfficerPrototype bankOfficer = new BankOfficerPrototype("username", "120", admin);

        Mockito.when(bankOfficerPrototypeRepository.findByUsername(Mockito.any())).thenReturn(bankOfficer);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .get("/adm/add-bank-officer-success/1");

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(model().attribute("title", "Success"))
                .andExpect(model().attribute("id", 1L));
    }

    @Test
    @WithMockUser(username = "user", password = "pass", roles = {"ADMIN"})
    public void reject() throws Exception {
        Admin admin = new Admin();
        BankOfficerPrototype bankOfficer = new BankOfficerPrototype("username", "120", admin);

        Mockito.when(bankOfficerPrototypeRepository.findByUsername(Mockito.any())).thenReturn(bankOfficer);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .get("/adm/add-bank-officer-reject/a");

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(model().attribute("title", "Reject"));
    }
}
