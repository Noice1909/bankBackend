package com.example.demo.junit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;

import com.example.demo.model.Account;
import com.example.demo.model.Customer;
import com.example.demo.repository.AccountRepository;
import com.example.demo.repository.CustomerRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AccountTest {
	private static final ObjectMapper om = new ObjectMapper();
	 
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private AccountRepository mockRepository;
	
	@BeforeEach 
	public void init() {
		Account account = new Account(161542426L, new Customer(1L), "Saving", new BigDecimal("10000"));
		 when(mockRepository.findById(161542426L)).thenReturn(Optional.of(account));
	}
	
	 @Test
	    public void find_accountId_OK() throws Exception {

	        this.mockMvc.perform(get("/api/v1/accounts/1"))
	                /*.andDo(print())*/
	                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
	                .andExpect(status().isOk())
	                .andExpect(jsonPath("$.account_id", is(161542426)));
//	                .andExpect(jsonPath("$.customer", is(new Customer(1L))))
//	                .andExpect(jsonPath("$.accountType", is("Saving")))
//	                .andExpect(jsonPath("$.balance", is(new BigDecimal("10000"))));

	        verify(mockRepository, times(1)).findById(161542426L);
	    }

	/*
	 @Test
	    public void find_customerIdNotFound_404() throws Exception {
	        mockMvc.perform(get("/accounts/5")).andExpect(status().isNotFound());
	    }
	 
	 @Test
	    public void saveCustomer() throws Exception {

		 Account account = new Account(1289802182L, new Customer(1L), "Saving", new BigDecimal("10000"));

	        mockMvc.perform(post("/api/v1/accounts/1")
//	                .content(om.writeValueAsString(customer))
	                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
	                
	        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.account_id", is(1289802182)))
            .andExpect(jsonPath("$.customer", is(new Customer(1L))))
            .andExpect(jsonPath("$.accountType", is("Saving")))
            .andExpect(jsonPath("$.balance", is(new BigDecimal("10000"))));

	        verify(mockRepository, times(1)).save(any(Account.class));

	    }
	 @Test
	 @Rollback(false)
	 public void testUpdateCustomer() {
	     Optional<Account> accounts  = mockRepository.findById(1289802182L);
	     Account acc = accounts.get();
	      System.out.print(acc.getBalance());

	     acc.setBalance(new BigDecimal("836353789"));
	      
	     
	     mockRepository.save(acc);
//	     mockRepository.r
	     Optional<Account> updatedAcc = mockRepository.findById(1289802182L);
	     Account updatedAccount = updatedAcc.get();
	      System.out.print(updatedAccount.getBalance());
	     assertThat(updatedAccount.getBalance()).isEqualTo("836353789");
	 }*/
	 
//	 @Test
//	 @Rollback(false)
//	 public void testDeleteCustomer() {
//		 Account cust = mockRepository.findById(1L).get();
//     
//		 mockRepository.deleteById(cust.getCustomerId());
//	      
//		 Account deletedCustomer = mockRepository.findById(1L).get();
//	      System.out.print("deleted customer: "+deletedCustomer.getFullName());
//	     assertThat(deletedCustomer).isNull();
//	 }
}
