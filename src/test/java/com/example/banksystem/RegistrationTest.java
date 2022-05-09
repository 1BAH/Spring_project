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
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
public class RegistrationTest {
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
    public void redirect() throws Exception {
        Client client = new Client(3,"user", "sur", "add", "pass");

        Mockito.when(clientRepository.save(Mockito.any())).thenReturn(client);

        System.out.println("user");

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get("/registration/form")
                .param("name", "user")
                .param("surname", "sur")
                .param("address", "add")
                .param("passport", "pass");

        mockMvc.perform(mockRequest)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }

    @Test
    public void nameIsEmpty() throws Exception {
        Client client = new Client(3,"user", "sur", "add", "pass");

        Mockito.when(clientRepository.save(Mockito.any())).thenReturn(client);

        System.out.println("user");

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get("/registration/form")
                .param("surname", "sur")
                .param("address", "add")
                .param("passport", "pass");

        mockMvc.perform(mockRequest)
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void surnameIsEmpty() throws Exception {
        Client client = new Client(3,"user", "sur", "add", "pass");

        Mockito.when(clientRepository.save(Mockito.any())).thenReturn(client);

        System.out.println("user");

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get("/registration/form")
                .param("name", "user")
                .param("address", "add")
                .param("passport", "pass");

        mockMvc.perform(mockRequest)
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void addressIsEmpty() throws Exception {
        Client client = new Client(3,"user", "sur", "add", "pass");

        Mockito.when(clientRepository.save(Mockito.any())).thenReturn(client);

        System.out.println("user");

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get("/registration/form")
                .param("name", "user")
                .param("surname", "sur")
                .param("passport", "pass");

        mockMvc.perform(mockRequest)
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void passportIsEmpty() throws Exception {
        Client client = new Client(3,"user", "sur", "add", "pass");

        Mockito.when(clientRepository.save(Mockito.any())).thenReturn(client);

        System.out.println("user");

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get("/registration/form")
                .param("name", "user")
                .param("surname", "sur")
                .param("address", "add");

        mockMvc.perform(mockRequest)
                .andExpect(status().is4xxClientError());
    }
}
