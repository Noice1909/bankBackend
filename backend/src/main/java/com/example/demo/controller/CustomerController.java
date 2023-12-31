package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Customer;
import com.example.demo.repository.CustomerRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class CustomerController {

	@Autowired
	private CustomerRepository customerRepository;

	public CustomerController(CustomerRepository customerRepo) {
		// TODO Auto-generated constructor stub
		this.customerRepository = customerRepo;
	}


	@GetMapping("/customers")
	public List<Customer> getAllCustomers() {
		return customerRepository.findAll();
	}
	 @GetMapping("/customers/{id}")
	 public ResponseEntity<Customer> getById(@PathVariable(value = "id") Long customerId)  throws ResourceNotFoundException{
			
			return ResponseEntity.ok( customerRepository.findById(customerId)
					.orElseThrow(() -> new ResourceNotFoundException("Customer not found for this id :: " + customerId)));

		}
	 
	 @PostMapping("/customers/{id}")
	 public ResponseEntity<Customer> getCustomerById(@PathVariable(value = "id") Long customerId)  throws ResourceNotFoundException{
			
			return ResponseEntity.ok( customerRepository.findById(customerId)
					.orElseThrow(() -> new ResourceNotFoundException("Customer not found for this id :: " + customerId)));

		}
	 
	 

	@PostMapping("/customers")
	public ResponseEntity<Map<String, Object>> createCustomer(@Validated @RequestBody Customer newCustomer) {
		Customer savedCustomer = customerRepository.save(newCustomer);
		System.out.println("inside the controller");
		Map<String, Object> response = new HashMap<>();
		response.put("success", true);
		response.put("message", "Customer created successfully");
		response.put("customer", savedCustomer);
		return ResponseEntity.ok(response);
	}

	@PutMapping("/customers/{id}")
	public ResponseEntity<Customer> updateCustomer(@PathVariable(value = "id") Long customerId,
												   @Validated @RequestBody Customer updatedCustomer) throws ResourceNotFoundException {
		Customer customer = customerRepository.findById(customerId)
				.orElseThrow(() -> new ResourceNotFoundException("Customer not found for this id :: " + customerId));

		customer.setFullName(updatedCustomer.getFullName());
		customer.setEmail(updatedCustomer.getEmail());
		customer.setPhone(updatedCustomer.getPhone());
		customer.setAddress(updatedCustomer.getAddress());
		customer.setCity(updatedCustomer.getCity());
		customer.setRegion(updatedCustomer.getRegion());
		customer.setPostalCode(updatedCustomer.getPostalCode());
		// Set other fields you want to update

		customerRepository.save(customer);

		return ResponseEntity.ok(customer);
	}

	@DeleteMapping("/customers/{id}")
	public Map<String, Boolean> deleteCustomer(@PathVariable(value = "id") Long customerId) throws ResourceNotFoundException {
		Customer customer = customerRepository.findById(customerId)
				.orElseThrow(() -> new ResourceNotFoundException("Customer not found for this id :: " + customerId));

		customerRepository.delete(customer);
		Map<String, Boolean> response = new HashMap<>();
		response.put("Customer has been Deleted", Boolean.TRUE);
		return response;
	}
}
