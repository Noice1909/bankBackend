package com.example.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Customer;
import com.example.demo.repository.CustomerRepository;

@RestController
@RequestMapping("/api/v1")
public class CustomerController {
	@Autowired
	private CustomerRepository customerRepository;
	
	@GetMapping("/customers")
	public List<Customer> getAllCustomers(){
		return customerRepository.findAll();
	}
	
	@PostMapping("/sendCustomer")
    public Customer createCustomer(@Validated @RequestBody Customer newCustomer) {
        return customerRepository.save(newCustomer);
    }
	
	@PutMapping("/updateCustomer/{id}")
	public ResponseEntity<Customer> updateCustomer(@PathVariable(value="id") Long userId,  @Validated @RequestBody Customer newCustomer) throws ResourceNotFoundException 
    {
		Customer updatedCustomer = customerRepository.findById(userId)
				.orElseThrow(()-> new ResourceNotFoundException("Customer is not avaialble:"+ userId));
		updatedCustomer.setName(newCustomer.getName());
		updatedCustomer.setMobileNo(newCustomer.getMobileNo());
		updatedCustomer.setAddress(newCustomer.getAddress());
		updatedCustomer.setEmailId(newCustomer.getEmailId());
		customerRepository.save(updatedCustomer);
		
		return ResponseEntity.ok(updatedCustomer);
    }
	
	@DeleteMapping("/deleteCustomer/{id}")
	public Map<String, Boolean> deleteCustomer(@PathVariable(value="id") Long userId) throws ResourceNotFoundException {
		Customer updatedCustomer = customerRepository.findById(userId)
				.orElseThrow(()-> new ResourceNotFoundException("Customer is not avaialble:"+ userId));
		customerRepository.delete(updatedCustomer);
		Map<String,Boolean> response  = new HashMap<>();
		response.put("Customer has been Deleted", Boolean.TRUE);
		return response;
		}
	
}
