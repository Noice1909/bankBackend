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
import com.example.demo.model.Customer;
import com.example.demo.repository.CustomerRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

//Java Program to Illustrate Unit Testing of Service Class


import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

//@ExtendWith(SpringExtension.class)
//@WebMvcTest(controllers = CustomerController.class)
 // for restTemplate

//@RunWith(MockitoJUnitRunner.class)
//@SpringBootTest
//@AutoConfigureMockMvc

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class CustomerTests {
	@Autowired
	 private ObjectMapper om ;
	 
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private CustomerRepository mockRepository;
	
	@BeforeEach 
	public void init() {
		Customer customer = new Customer(1L, "Rahul", "rahul@gmail.com", "872673732826", 
				"H-778 st-26 Madhura Nagar", "Hydrabad", "Hindu", "500083");
		 when(mockRepository.findById(1L)).thenReturn(Optional.of(customer));
	}
	
	 @Test
	    public void find_customerId_OK() throws Exception {

	        this.mockMvc.perform(get("/api/v1/customers/1"))
	                /*.andDo(print())*/
	                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
	                .andExpect(status().isOk())
	                .andExpect(jsonPath("$.customerId", is(1)))
	                .andExpect(jsonPath("$.fullName", is("Rahul")))
	                .andExpect(jsonPath("$.email", is("rahul@gmail.com")))
	                .andExpect(jsonPath("$.phone", is("872673732826")))
	                .andExpect(jsonPath("$.address", is("H-778 st-26 Madhura Nagar")))
	                .andExpect(jsonPath("$.city", is("Hydrabad")))
	                .andExpect(jsonPath("$.region", is("Hindu")))
	                .andExpect(jsonPath("$.postalCode", is("500083")));

	        verify(mockRepository, times(1)).findById(1L);
	    }

	
	 @Test
	    public void find_customerIdNotFound_404() throws Exception {
	        mockMvc.perform(get("/customers/5")).andExpect(status().isNotFound());
	    }
	 
//	 @Test
//	    public void saveCustomer() throws Exception {
////
//		 Customer customer = new Customer(1L, "Henry", "rahul@gmail.com", "872673732826", 
//					"H-778 st-26 Madhura Nagar", "Hydrabad", "Hindu", "500083");
//		mockRepository.save(customer);
//	        mockMvc.perform(post("http://localhost:8080/api/v1/saveCustomers")
//	                .content(om.writeValueAsString(customer))
//	                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
//	                
//			        .andExpect(jsonPath("$.customerId", is(1)))
//		            .andExpect(jsonPath("$.fullName", is("Henry")))
//		            .andExpect(jsonPath("$.email", is("rahul@gmail.com")))
//		            .andExpect(jsonPath("$.phone", is("872673732826")))
//		            .andExpect(jsonPath("$.address", is("H-778 st-26 Madhura Nagar")))
//		            .andExpect(jsonPath("$.city", is("Hydrabad")))
//		            .andExpect(jsonPath("$.region", is("Hindu")))
//		            .andExpect(jsonPath("$.postalCode", is("500083")));
//
//	        verify(mockRepository, times(1)).save(any(Customer.class));
////
//	    }
	 @Test
	 @Rollback(false)
	 public void testUpdateCustomer() {
	     Optional<Customer> customer  = mockRepository.findById(1L);
	     Customer cust = customer.get();
	      System.out.print(cust.getFullName());

	     cust.setFullName("Tom jerry");
	      
	     
	     mockRepository.save(cust);
//	     mockRepository.r
	     Optional<Customer> updatedCust = mockRepository.findById(1L);
	     Customer updatedCustomer = updatedCust.get();
	      System.out.print(updatedCustomer.getFullName());
	     assertThat(updatedCustomer.getFullName()).isEqualTo("Tom jerry");
	 }
	 
//	 @Test
//	 @Rollback(false)
//	 public void testDeleteCustomer() throws Exception {
//		 doNothing().when(mockRepository).deleteById(1L);
//
//	        try {
//				mockMvc.perform(delete("http://localhost:8080/api/v1/customers/2"))
//				        .andExpect(status().isOk());
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//
//	       verify(mockRepository, times(1)).deleteById(1L);
//	 }
	 
//	 @Test
//	 @Rollback(false)
//	 public void testDelete() throws Exception{
//		 Optional<Customer> customer = mockRepository.findById(1L);
//					
//
//		mockRepository.delete(customer.get());
//	     Optional<Customer> deletedCust = mockRepository.findById(1L);
//	     Customer deletedCustomer = deletedCust.get();
//	      System.out.print("\n deleted customer: "+deletedCustomer.getFullName());
//	     assertThat(deletedCustomer).isNull();
//		 
//	 }
	
	 
}
