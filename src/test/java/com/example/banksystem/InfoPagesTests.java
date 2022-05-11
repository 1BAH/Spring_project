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
public class InfoPagesTests {
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
    public void faqUnautorised() throws Exception {
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .get("/faq");

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(model().attribute("title", "FAQ"));
    }

    @Test
    public void authorsUnautorised() throws Exception {
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .get("/authors");

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(model().attribute("title", "About us"));
    }

    @Test
    @WithMockUser(username = "name", password = "pass")
    public void faqAtorised() throws Exception {
        Client client = new Client(3, "user", "sur", "add", "pass");
        Mockito.when(clientRepository.findByPassport(Mockito.any())).thenReturn(client);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .get("/faq");

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(model().attribute("title", "FAQ"));
    }

    @Test
    @WithMockUser(username = "name", password = "pass")
    public void authorsAutorised() throws Exception {
        Client client = new Client(3, "user", "sur", "add", "pass");
        Mockito.when(clientRepository.findByPassport(Mockito.any())).thenReturn(client);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .get("/authors");

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(model().attribute("title", "About us"));
    }
}
