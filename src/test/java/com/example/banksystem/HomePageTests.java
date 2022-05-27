package com.example.banksystem;

import com.example.banksystem.models.Admin;
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
    BankOfficerRepository bankOfficerRepository;

    @MockBean
    AdminRepository adminRepository;

    @MockBean
    BankOfficerPrototypeRepository bankOfficerPrototypeRepository;

    @MockBean
    CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    @Test
    @WithMockUser(username = "user", password = "pass")
    public void homePageClient() throws Exception {
        Client client = new Client(3,"user", "sur", "add", "pass");

        Mockito.when(clientRepository.findByPassport(Mockito.any())).thenReturn(client);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get("/home");

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(model().attribute("user", client))
                .andExpect(model().attribute("title", "Home"));
    }

    @Test
    @WithMockUser(username = "user", password = "pass")
    public void homePageBankOfficer() throws Exception {
        BankOfficer bankOfficer = new BankOfficer("username", "120");

        Mockito.when(bankOfficerRepository.findByUsername(Mockito.any())).thenReturn(bankOfficer);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get("/bo/home");

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(model().attribute("user", bankOfficer))
                .andExpect(model().attribute("title", "Home"));
    }

    @Test
    @WithMockUser(username = "root", password = "root", roles = {"ADMIN"})
    public void homePageAdmin() throws Exception {
        Admin admin = new Admin();

        Mockito.when(adminRepository.findByName(Mockito.any())).thenReturn(admin);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get("/adm");

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(model().attribute("user", admin))
                .andExpect(model().attribute("title", "Home page"));
    }

    @Test
    @WithMockUser(username = "root", password = "root", roles = {"USER"})
    public void homePageAdmin403() throws Exception {
        Admin admin = new Admin();

        Mockito.when(adminRepository.findByName(Mockito.any())).thenReturn(admin);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get("/adm");

        mockMvc.perform(mockRequest)
                .andExpect(status().isForbidden());
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
    public void rootAuthorisedClient() throws Exception {
        Client client = new Client(3,"user", "sur", "add", "pass");

        Mockito.when(clientRepository.findByPassport(Mockito.any())).thenReturn(client);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get("/");

        mockMvc.perform(mockRequest)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/home"));
    }

    @Test
    @WithMockUser(username = "user", password = "pass")
    public void rootAuthorisedBankOfficer() throws Exception {
        BankOfficer bankOfficer = new BankOfficer("username", "120");

        Mockito.when(bankOfficerRepository.findByUsername(Mockito.any())).thenReturn(bankOfficer);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get("/");

        mockMvc.perform(mockRequest)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/bo/home"));
    }

    @Test
    @WithMockUser(username = "root", password = "root")
    public void rootAuthorisedAdmin() throws Exception {
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get("/");

        mockMvc.perform(mockRequest)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/adm"));
    }
}
