package com.example.demo.junit;

import com.example.demo.model.Account;
import com.example.demo.model.Customer;
import com.example.demo.model.Withdrawal;
import com.example.demo.repository.AccountRepository;
import com.example.demo.repository.UserAuthRepository;
import com.example.demo.repository.WithdrawalRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import com.example.demo.controller.WithdrawalController;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(WithdrawalController.class)
public class WithdrawalControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WithdrawalRepository withdrawalRepository;

    @MockBean
    private AccountRepository accountRepository;

    @MockBean
    private UserAuthRepository userAuthRepository;

    @InjectMocks
    private WithdrawalController withdrawalController;

    private Customer customer;
    private Account account;
    private Withdrawal withdrawal;

    @BeforeEach
    public void setUp() {
        customer = new Customer(1L, "John Doe", "john@example.com", "1234567890", "123 Main St", "Cityville", "Regionland", "12345");
        account = new Account(customer, "Savings", BigDecimal.valueOf(2000), "ACTIVE");
        withdrawal = new Withdrawal(account, customer, BigDecimal.valueOf(500));
    }

    @Test
    public void testGetAllWithdrawals() throws Exception {
        List<Withdrawal> withdrawals = new ArrayList<>();
        withdrawals.add(withdrawal);

        when(withdrawalRepository.findAll()).thenReturn(withdrawals);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/withdrawal"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(1));
    }

    @Test
    public void testCreateWithdrawal() throws Exception {
        when(withdrawalRepository.save(any(Withdrawal.class))).thenReturn(withdrawal);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/withdrawal")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"account\": {\"accountId\": 1}, \"customer\": {\"customerId\": 1}, \"withdrawalAmount\": 500}"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true));
    }

    // Similar tests for other methods

    @Test
    void testUpdateWithdrawalById() throws Exception {
        Withdrawal existingWithdrawal = withdrawal;
        Withdrawal updatedWithdrawal = new Withdrawal(account, customer, BigDecimal.valueOf(800));
        when(withdrawalRepository.findById(anyLong())).thenReturn(Optional.of(existingWithdrawal));
        when(withdrawalRepository.save(any(Withdrawal.class))).thenReturn(updatedWithdrawal);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/withdrawal/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(updatedWithdrawal))) // Convert updatedWithdrawal to JSON
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.withdrawalAmount").value(updatedWithdrawal.getWithdrawalAmount()));

    }

    @Test
    void testUpdateWithdrawalStatus() throws Exception {
        Withdrawal existingWithdrawal = withdrawal;
        Withdrawal updatedWithdrawal = new Withdrawal(account, customer, BigDecimal.valueOf(200));
        when(withdrawalRepository.findById(anyLong())).thenReturn(Optional.of(existingWithdrawal));
        when(withdrawalRepository.save(any(Withdrawal.class))).thenReturn(updatedWithdrawal);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/withdrawal/1/status")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("completed"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("completed"));
    }

    @Test
    public void testRequestWithdrawal() throws Exception {
        when(accountRepository.findById(anyLong())).thenReturn(java.util.Optional.of(account));
        when(userAuthRepository.findByCustomer_CustomerId(anyLong())).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/request")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"account\": {\"accountId\": 1}, \"customer\": {\"customerId\": 1}, \"withdrawalAmount\": 500}")
                        .param("password", "password"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(false));
    }

    private String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}


