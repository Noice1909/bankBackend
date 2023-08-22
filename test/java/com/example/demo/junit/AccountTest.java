package com.example.demo.junit;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.example.demo.controller.AccountController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;


import com.example.demo.model.Account;
import com.example.demo.model.Customer;
import com.example.demo.repository.AccountRepository;
import com.example.demo.repository.CustomerRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

//@WebMvcTest(AccountController.class)
@SpringBootTest
@AutoConfigureMockMvc
//@RunWith(SpringRunner.class)
//@ActiveProfiles("test")
public class AccountTest {
       @MockBean
       AccountRepository accountRepository;

       @Autowired
       MockMvc mockMvc;

       // POST
//       @Test
//    public void test_saveAccount() throws Exception {
//           Account account = new Account();
//           account.setAccountId(100L);
//           account.setAccountType("savings");
//           account.setBalance(new BigDecimal(50089));
//           when(accountRepository.save(account)).thenReturn(account);
//           mockMvc.perform(post("/api/v1/accounts")
//                   .contentType(MediaType.APPLICATION_JSON)
//                   .content(new ObjectMapper().writeValueAsString(account)))
//                   .andExpect(status().isOk());
//
//       }

       // GET by id
       @Test
       public void test_getAccountById() throws Exception {
              Account account = new Account();
              account.setAccountId(100L);
              account.setAccountType("savings");
              account.setBalance(new BigDecimal("50089"));
              when(accountRepository.findById(100L)).thenReturn(Optional.of(account));
              mockMvc.perform(get("/api/v1/accounts/{id}",100L)
                              .contentType(MediaType.APPLICATION_JSON))
                      .andExpect(status().isOk())
                      .andExpect(jsonPath("$accountType").value("savings"));
       }

       // GET
       @Test
        public void test_getAccountList() throws Exception{
           List<Account> accountList = new ArrayList<Account>();
           Account account = new Account();
           account.setAccountId(100L);
           account.setAccountType("savings");
           account.setBalance(new BigDecimal("50089"));
           accountList.add(account);

           Account account2 = new Account();
           account2.setAccountId(200L);
           account2.setAccountType("current");
           account2.setBalance(new BigDecimal("8450089"));
           accountList.add(account2);

           when(accountRepository.findAll()).thenReturn(accountList);
           mockMvc.perform(get("/accounts")
                   .contentType(MediaType.APPLICATION_JSON))
                   .andExpect(status().isOk())
                   .andExpect(jsonPath("$[0].accountType").value("savings"));

       }

       //DELETE
        @Test
        public void test_deleteAccount() throws Exception{
           Account account = new Account();
            account.setAccountId(100L);
            account.setAccountType("savings");
            account.setBalance(new BigDecimal("50089"));
            doNothing().when(accountRepository).delete(account);
            mockMvc.perform(delete("http://localhost:8080/api/v1/accounts/{id}", 100L)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(new ObjectMapper().writeValueAsString(account)))
                    .andExpect(status().isOk());
        }

}
