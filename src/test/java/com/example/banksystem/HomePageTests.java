package com.example.banksystem;

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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
public class HomePageTests {
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
    public void homePage() throws Exception {
        Client client = new Client(3,"user", "sur", "add", "pass");

        Mockito.when(clientRepository.findByPassport(Mockito.any())).thenReturn(client);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get("/home");

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(model().attribute("user", client))
                .andExpect(model().attribute("title", "Home"));
    }

    @Test
    public void startPage() throws Exception {
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get("/start");

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(model().attribute("title", "Start work"));
    }

    @Test
    public void rootUnauthorised() throws Exception {
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get("/");

        mockMvc.perform(mockRequest)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/start"));
    }

    @Test
    @WithMockUser(username = "user", password = "pass")
    public void rootAuthorised() throws Exception {
        Client client = new Client(3,"user", "sur", "add", "pass");

        Mockito.when(clientRepository.findByPassport(Mockito.any())).thenReturn(client);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get("/");

        mockMvc.perform(mockRequest)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/home"));
    }
}
