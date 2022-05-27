package com.example.banksystem.forbidden;

import com.example.banksystem.models.BankOfficer;
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

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class BankOfficer403 {
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
    public void boHome() throws Exception {
        BankOfficer bankOfficer = new BankOfficer("username", "120");

        Mockito.when(bankOfficerRepository.findByUsername(Mockito.any())).thenReturn(bankOfficer);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get("/home");

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(model().attribute("title", "403 FORBIDDEN"));
    }

    @Test
    @WithMockUser(username = "user", password = "pass")
    public void boWithdrawChoose() throws Exception {
        BankOfficer bankOfficer = new BankOfficer("username", "120");

        Mockito.when(bankOfficerRepository.findByUsername(Mockito.any())).thenReturn(bankOfficer);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .get("/withdraw/choose")
                .param("accountId", "324");

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(model().attribute("title", "403 FORBIDDEN"));
    }

    @Test
    @WithMockUser(username = "user", password = "pass")
    public void boWithdrawProcess() throws Exception {
        BankOfficer bankOfficer = new BankOfficer("username", "120");

        Mockito.when(bankOfficerRepository.findByUsername(Mockito.any())).thenReturn(bankOfficer);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .get("/withdraw/3")
                .param("amount", "324");

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(model().attribute("title", "403 FORBIDDEN"));
    }

    @Test
    @WithMockUser(username = "user", password = "pass")
    public void boPutChoose() throws Exception {
        BankOfficer bankOfficer = new BankOfficer("username", "120");

        Mockito.when(bankOfficerRepository.findByUsername(Mockito.any())).thenReturn(bankOfficer);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .get("/put/choose")
                .param("accountId", "324");

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(model().attribute("title", "403 FORBIDDEN"));
    }

    @Test
    @WithMockUser(username = "user", password = "pass")
    public void boPutProcess() throws Exception {
        BankOfficer bankOfficer = new BankOfficer("username", "120");

        Mockito.when(bankOfficerRepository.findByUsername(Mockito.any())).thenReturn(bankOfficer);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .get("/put/3")
                .param("amount", "324");

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(model().attribute("title", "403 FORBIDDEN"));
    }

    @Test
    @WithMockUser(username = "user", password = "pass")
    public void boTransactions() throws Exception {
        BankOfficer bankOfficer = new BankOfficer("username", "120");

        Mockito.when(bankOfficerRepository.findByUsername(Mockito.any())).thenReturn(bankOfficer);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get("/transactions");

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(model().attribute("title", "403 FORBIDDEN"));
    }

    @Test
    @WithMockUser(username = "user", password = "pass")
    public void boTransaction1() throws Exception {
        BankOfficer bankOfficer = new BankOfficer("username", "120");

        Mockito.when(bankOfficerRepository.findByUsername(Mockito.any())).thenReturn(bankOfficer);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get("/transactions/make/step-1");

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(model().attribute("title", "403 FORBIDDEN"));
    }

    @Test
    @WithMockUser(username = "user", password = "pass")
    public void boTransaction2() throws Exception {
        BankOfficer bankOfficer = new BankOfficer("username", "120");

        Mockito.when(bankOfficerRepository.findByUsername(Mockito.any())).thenReturn(bankOfficer);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get("/transactions/make/step-2/1");

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(model().attribute("title", "403 FORBIDDEN"));
    }

    @Test
    @WithMockUser(username = "user", password = "pass")
    public void boTransaction2self() throws Exception {
        BankOfficer bankOfficer = new BankOfficer("username", "120");

        Mockito.when(bankOfficerRepository.findByUsername(Mockito.any())).thenReturn(bankOfficer);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get("/transactions/make/step-2-self/1");

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(model().attribute("title", "403 FORBIDDEN"));
    }

    @Test
    @WithMockUser(username = "user", password = "pass")
    public void boTransaction3() throws Exception {
        BankOfficer bankOfficer = new BankOfficer("username", "120");

        Mockito.when(bankOfficerRepository.findByUsername(Mockito.any())).thenReturn(bankOfficer);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get("/transactions/make/step-3/1/1");

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(model().attribute("title", "403 FORBIDDEN"));
    }

    @Test
    @WithMockUser(username = "user", password = "pass")
    public void boTransaction4() throws Exception {
        BankOfficer bankOfficer = new BankOfficer("username", "120");

        Mockito.when(bankOfficerRepository.findByUsername(Mockito.any())).thenReturn(bankOfficer);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .get("/transactions/make/step-4/1/1/form")
                .param("amount", "1");

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(model().attribute("title", "403 FORBIDDEN"));
    }

    @Test
    @WithMockUser(username = "user", password = "pass")
    public void boTransactionError() throws Exception {
        BankOfficer bankOfficer = new BankOfficer("username", "120");

        Mockito.when(bankOfficerRepository.findByUsername(Mockito.any())).thenReturn(bankOfficer);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get("/transactions/transaction-error");

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(model().attribute("title", "403 FORBIDDEN"));
    }

    @Test
    @WithMockUser(username = "user", password = "pass")
    public void boTransactionInfo() throws Exception {
        BankOfficer bankOfficer = new BankOfficer("username", "120");

        Mockito.when(bankOfficerRepository.findByUsername(Mockito.any())).thenReturn(bankOfficer);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get("/transaction/1");

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(model().attribute("title", "403 FORBIDDEN"));
    }

    @Test
    @WithMockUser(username = "user", password = "pass")
    public void boAccounts() throws Exception {
        BankOfficer bankOfficer = new BankOfficer("username", "120");

        Mockito.when(bankOfficerRepository.findByUsername(Mockito.any())).thenReturn(bankOfficer);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get("/accounts");

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(model().attribute("title", "403 FORBIDDEN"));
    }

    @Test
    @WithMockUser(username = "user", password = "pass")
    public void boAccountsAdd() throws Exception {
        BankOfficer bankOfficer = new BankOfficer("username", "120");

        Mockito.when(bankOfficerRepository.findByUsername(Mockito.any())).thenReturn(bankOfficer);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get("/accounts/add");

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(model().attribute("title", "403 FORBIDDEN"));
    }

    @Test
    @WithMockUser(username = "user", password = "pass")
    public void boAccountsAddForm() throws Exception {
        BankOfficer bankOfficer = new BankOfficer("username", "120");

        Mockito.when(bankOfficerRepository.findByUsername(Mockito.any())).thenReturn(bankOfficer);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .get("/accounts/add/form")
                .param("type", "c")
                .param("bankId", "3");

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(model().attribute("title", "403 FORBIDDEN"));
    }

    @Test
    @WithMockUser(username = "user", password = "pass")
    public void boAccountDelete() throws Exception {
        BankOfficer bankOfficer = new BankOfficer("username", "120");

        Mockito.when(bankOfficerRepository.findByUsername(Mockito.any())).thenReturn(bankOfficer);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get("/accounts/delete/5");

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(model().attribute("title", "403 FORBIDDEN"));
    }


    @Test
    @WithMockUser(username = "user", password = "pass")
    public void boAccountDeleteTry() throws Exception {
        BankOfficer bankOfficer = new BankOfficer("username", "120");

        Mockito.when(bankOfficerRepository.findByUsername(Mockito.any())).thenReturn(bankOfficer);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get("/account/delete/try/5");

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(model().attribute("title", "403 FORBIDDEN"));
    }

    @Test
    @WithMockUser(username = "user", password = "pass")
    public void boProfile() throws Exception {
        BankOfficer bankOfficer = new BankOfficer("username", "120");

        Mockito.when(bankOfficerRepository.findByUsername(Mockito.any())).thenReturn(bankOfficer);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get("/profile");

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(model().attribute("title", "403 FORBIDDEN"));
    }

    @Test
    @WithMockUser(username = "user", password = "pass")
    public void boPut() throws Exception {
        BankOfficer bankOfficer = new BankOfficer("username", "120");

        Mockito.when(bankOfficerRepository.findByUsername(Mockito.any())).thenReturn(bankOfficer);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get("/put");

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(model().attribute("title", "403 FORBIDDEN"));
    }

    @Test
    @WithMockUser(username = "user", password = "pass")
    public void boWithdraw() throws Exception {
        BankOfficer bankOfficer = new BankOfficer("username", "120");

        Mockito.when(bankOfficerRepository.findByUsername(Mockito.any())).thenReturn(bankOfficer);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get("/withdraw");

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(model().attribute("title", "403 FORBIDDEN"));
    }

    @Test
    @WithMockUser(username = "user", password = "pass")
    public void boAccountDeleteRequest() throws Exception {
        BankOfficer bankOfficer = new BankOfficer("username", "120");

        Mockito.when(bankOfficerRepository.findByUsername(Mockito.any())).thenReturn(bankOfficer);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get("/account/account-delete-request");

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(model().attribute("title", "403 FORBIDDEN"));
    }

    @Test
    @WithMockUser(username = "user", password = "pass")
    public void boWithdrawError() throws Exception {
        BankOfficer bankOfficer = new BankOfficer("username", "120");

        Mockito.when(bankOfficerRepository.findByUsername(Mockito.any())).thenReturn(bankOfficer);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get("/withdraw/withdraw-error");

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(model().attribute("title", "403 FORBIDDEN"));
    }
}
