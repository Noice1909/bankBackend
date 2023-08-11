
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
import com.example.demo.model.Account;
import com.example.demo.repository.AccountRepository;

@RestController
@RequestMapping("/api/v1")
public class AccountController {
	@Autowired
	private AccountRepository accountRepository;
	
	@GetMapping("/accounts")
	public List<Account> getAllAccounts(){
		return accountRepository.findAll();
	}
	
	@PostMapping("/sendAccount")
    public Account createAccount(@Validated @RequestBody Account newAccount) {
        return accountRepository.save(newAccount);
    }
	
	@PutMapping("/updateAccount/{id}")
	public ResponseEntity<Account> updateAccount(@PathVariable(value="id") Long userId,  @Validated @RequestBody Account newAccount) throws ResourceNotFoundException 
    {
		Account updatedAccount = accountRepository.findById(userId)
				.orElseThrow(()-> new ResourceNotFoundException("Account is not avaialble:"+ userId));
		updatedAccount.setAccNo(newAccount.getAccNo());
		updatedAccount.setAccType(newAccount.getAccType());
		updatedAccount.setBalance(newAccount.getBalance());
		accountRepository.save(updatedAccount);
		
		return ResponseEntity.ok(updatedAccount);
    }
	
	@DeleteMapping("/deleteAccount/{id}")
	public Map<String, Boolean> deleteAccount(@PathVariable(value="id") Long userId) throws ResourceNotFoundException {
		Account updatedAccount = accountRepository.findById(userId)
				.orElseThrow(()-> new ResourceNotFoundException("Account is not avaialble:"+ userId));
		accountRepository.delete(updatedAccount);
		Map<String,Boolean> response  = new HashMap<>();
		response.put("Account has been Deleted", Boolean.TRUE);
		return response;
		}
	
}
