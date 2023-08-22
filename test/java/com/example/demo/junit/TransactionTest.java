package com.example.demo.junit;

import org.json.JSONException;
import org.junit.BeforeClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
//import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.example.demo.controller.CustomerController;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Account;
import com.example.demo.model.Customer;
import com.example.demo.model.Transaction;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.repository.TransactionRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

//Java Program to Illustrate Unit Testing of Service Class


import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
//@ExtendWith(MockitoExtension.class)
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

//@WebMvcTest(TransactionController.class)
@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class TransactionTest {
	@Autowired
	private MockMvc mockMvc;
	 
	@MockBean
	private TransactionRepository transactionRepository;
	
	
	@BeforeEach
	public void init()
	{
		Transaction transaction1 = new Transaction(1L, new Account(1L), "9876543210", new BigDecimal("282828"), new Date());		
//        Long accountId=1L;
        when(transactionRepository.findById(1L)).thenReturn(Optional.of(transaction1));
	
		
	}
	
	
	@Test
    public void find_transactionId_OK() throws Exception {

        this.mockMvc.perform(get("/api/v1/transactions/1"))
                /*.andDo(print())*/
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.transactionId", is(1)));
//                .andExpect(jsonPath("$.fullName", is("Rahul")))
//                .andExpect(jsonPath("$.email", is("rahul@gmail.com")))
//                .andExpect(jsonPath("$.phone", is("872673732826")))
//                .andExpect(jsonPath("$.address", is("H-778 st-26 Madhura Nagar")))
//                .andExpect(jsonPath("$.city", is("Hydrabad")))
//                .andExpect(jsonPath("$.region", is("Hindu")))
//                .andExpect(jsonPath("$.postalCode", is("500083")));

        verify(transactionRepository, times(1)).findById(1L);
    }

}
