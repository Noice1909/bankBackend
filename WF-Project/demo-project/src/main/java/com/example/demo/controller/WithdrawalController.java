package com.example.demo.controller;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Account;
import com.example.demo.model.UserAuth;
import com.example.demo.model.Withdrawal;
import com.example.demo.repository.AccountRepository;
import com.example.demo.repository.UserAuthRepository;
import com.example.demo.repository.WithdrawalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("api/v1")
public class WithdrawalController {

    @Autowired
    private WithdrawalRepository withdrawalRepository;
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UserAuthRepository userAuthRepository;

    @GetMapping("/withdrawal")
    public List<Withdrawal> getAllWithdrawals() {
        return withdrawalRepository.findAll();
    }

    @PostMapping("/withdrawal")
    public ResponseEntity<Map<String, Object>> createAccount(@Validated @RequestBody Withdrawal newWithdrawal) {
        Map<String, Object> response = new HashMap<>();
        Withdrawal createdWithdrawal = withdrawalRepository.save(newWithdrawal);
        response.put("success", true);
        response.put("message", "Account created successfully");
        response.put("withdrawal", createdWithdrawal);

        return ResponseEntity.ok(response);
    }
    @PutMapping("/withdrawal/{id}")
    public ResponseEntity<Withdrawal> updateWithdrawal(@PathVariable(value = "id") Long withdrawalId,
                                                 @Validated @RequestBody Withdrawal updatedWithdrawal) throws ResourceNotFoundException {
        Withdrawal withdrawal = withdrawalRepository.findById(withdrawalId)
                .orElseThrow(() -> new ResourceNotFoundException("Withdrawal not found for this id :: " + withdrawalId));

        withdrawal.setWithdrawalAmount(updatedWithdrawal.getWithdrawalAmount());
        withdrawal.setAccount(updatedWithdrawal.getAccount());
        withdrawal.setStatus(updatedWithdrawal.getStatus());
        withdrawalRepository.save(withdrawal);

        return ResponseEntity.ok(withdrawal);
    }
    @PutMapping("/withdrawal/{id}/status")
    public ResponseEntity<Withdrawal> updateWithdrawalStatus(@PathVariable(value = "id") Long withdrawalId,
                                                       @Validated @RequestBody String newStatus) throws ResourceNotFoundException {
        Withdrawal withdrawal = withdrawalRepository.findById(withdrawalId)
                .orElseThrow(() -> new ResourceNotFoundException("Withdrawal not found for this id :: " + withdrawalId));

        withdrawal.setStatus(newStatus);
        withdrawalRepository.save(withdrawal);

        return ResponseEntity.ok(withdrawal);
    }
    @PostMapping("/request")
    public ResponseEntity<Map<String, Object>> requestWithdrawal(
            @RequestBody Withdrawal withdrawalRequest,
            @RequestParam String password) {
        Map<String, Object> response = new HashMap<>();

        Account account = accountRepository.findById(withdrawalRequest.getAccount().getAccountId()).orElse(null);

        if (account == null) {
            response.put("success", false);
            response.put("message", "Invalid account");
            return ResponseEntity.ok(response);
        }

        BigDecimal accountBalance = account.getBalance();
        BigDecimal withdrawalAmount = withdrawalRequest.getWithdrawalAmount();

        if (withdrawalAmount.compareTo(BigDecimal.ZERO) <= 0 || withdrawalAmount.compareTo(accountBalance) > 0) {
            response.put("success", false);
            response.put("message", "Invalid withdrawal amount or insufficient funds");
            return ResponseEntity.ok(response);
        }

        // Retrieve user authentication details
        UserAuth user = userAuthRepository.findByCustomer_CustomerId(withdrawalRequest.getCustomer().getCustomerId());

        if (user == null || !user.getPassword().equals(password)) {
            response.put("success", false);
            response.put("message", "Invalid credentials");
            return ResponseEntity.ok(response);
        }

        // Deduct the withdrawal amount from the account and update the balance
        account.setBalance(accountBalance.subtract(withdrawalAmount));
        accountRepository.save(account);

        // Create and save the withdrawal transaction
        Withdrawal withdrawalTransaction = new Withdrawal(account, withdrawalRequest.getCustomer(), withdrawalAmount);
        withdrawalRepository.save(withdrawalTransaction);

        response.put("success", true);
        response.put("message", "Withdrawal request successful");
        return ResponseEntity.ok(response);
    }


}


