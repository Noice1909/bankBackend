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
import com.example.demo.model.Customer;
import com.example.demo.repository.CustomerRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

//Java Program to Illustrate Unit Testing of Service Class


import static org.mockito.Mockito.verify;
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
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
//@ExtendWith(MockitoExtension.class)
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = CustomerController.class)
 // for restTemplate
//@RunWith(SpringRunner.class)
//@SpringBootTest
//@AutoConfigureMockMvc
@ActiveProfiles("test")
public class CustomerTests {
	
	 private static final ObjectMapper om = new ObjectMapper();
	 
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
	 
	 @Test
	    public void saveCustomer() throws Exception {

		 Customer customer = new Customer(1L, "Rahul", "rahul@gmail.com", "872673732826", 
					"H-778 st-26 Madhura Nagar", "Hydrabad", "Hindu", "500083");

	        mockMvc.perform(post("/api/v1/customers/1")
//	                .content(om.writeValueAsString(customer))
	                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
	                
			        .andExpect(jsonPath("$.customerId", is(1)))
		            .andExpect(jsonPath("$.fullName", is("Rahul")))
		            .andExpect(jsonPath("$.email", is("rahul@gmail.com")))
		            .andExpect(jsonPath("$.phone", is("872673732826")))
		            .andExpect(jsonPath("$.address", is("H-778 st-26 Madhura Nagar")))
		            .andExpect(jsonPath("$.city", is("Hydrabad")))
		            .andExpect(jsonPath("$.region", is("Hindu")))
		            .andExpect(jsonPath("$.postalCode", is("500083")));

	        verify(mockRepository, times(1)).save(any(Customer.class));

	    }
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
	 
	 @Test
	 @Rollback(false)
	 public void testDeleteCustomer() {
		 Customer cust = mockRepository.findById(1L).get();
     
		 mockRepository.deleteById(cust.getCustomerId());
	      
		 Customer deletedCustomer = mockRepository.findById(1L).get();
	      System.out.print("deleted customer: "+deletedCustomer.getFullName());
	     assertThat(deletedCustomer).isNull();
	 }
	
}
