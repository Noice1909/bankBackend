package com.example.demo.junit;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.example.demo.controller.CustomerController;
import com.example.demo.model.Customer;
import com.example.demo.repository.CustomerRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerRepository customerRepository;

    @Test
    public void testGetAllCustomers() throws Exception {
        List<Customer> customers = new ArrayList<>();
        customers.add(new Customer(1L, "John Doe", "john@example.com", "1234567890", "123 Main St", "City", "Region", "12345"));
        customers.add(new Customer(2L, "Jane Smith", "jane@example.com", "9876543210", "456 Oak St", "Town", "State", "54321"));

        when(customerRepository.findAll()).thenReturn(customers);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/customers"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(customers.size()));
    }

    @Test
    public void testGetCustomerById() throws Exception {
        Customer customer = new Customer(1L, "John Doe", "john@example.com", "1234567890", "123 Main St", "City", "Region", "12345");

        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/customers/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.fullName").value(customer.getFullName()));
    }

    @Test
    public void testCreateCustomer() throws Exception {
        Customer newCustomer = new Customer(null, "New Customer", "new@example.com", "5555555555", "789 Elm St", "Village", "County", "67890");
        Customer savedCustomer = new Customer(3L, "New Customer", "new@example.com", "5555555555", "789 Elm St", "Village", "County", "67890");

        when(customerRepository.save(any(Customer.class))).thenReturn(savedCustomer);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(newCustomer)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.customer.fullName").value(savedCustomer.getFullName()));
    }

    @Test
    public void testUpdateCustomer() throws Exception {
        Customer existingCustomer = new Customer(1L, "John Doe", "john@example.com", "1234567890", "123 Main St", "City", "Region", "12345");
        Customer updatedCustomer = new Customer(1L, "Updated Customer", "updated@example.com", "9999999999", "987 Elm St", "Town", "State", "54321");

        when(customerRepository.findById(1L)).thenReturn(Optional.of(existingCustomer));
        when(customerRepository.save(any(Customer.class))).thenReturn(updatedCustomer);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/customers/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(updatedCustomer)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.fullName").value(updatedCustomer.getFullName()));
    }

    @Test
    public void testDeleteCustomer() throws Exception {
        Customer existingCustomer = new Customer(1L, "John Doe", "john@example.com", "1234567890", "123 Main St", "City", "Region", "12345");

        when(customerRepository.findById(1L)).thenReturn(Optional.of(existingCustomer));

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/customers/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.Customer has been Deleted").value(true));

        verify(customerRepository, times(1)).delete(existingCustomer);
    }

    // Utility method to convert an object to its JSON representation
    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
