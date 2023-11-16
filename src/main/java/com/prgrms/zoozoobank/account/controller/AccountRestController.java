package com.prgrms.zoozoobank.account.controller;

import com.prgrms.zoozoobank.account.domain.Account;
import com.prgrms.zoozoobank.account.service.AccountService;
import com.prgrms.zoozoobank.bankbranch.service.BankBranchService;
import com.prgrms.zoozoobank.customer.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/accounts")
public class AccountRestController {

    private final AccountService accountService;
    private final CustomerService customerService;
    private final BankBranchService bankBranchService;

    public AccountRestController(AccountService accountService, CustomerService customerService, BankBranchService bankBranchService) {
        this.accountService = accountService;
        this.customerService = customerService;
        this.bankBranchService = bankBranchService;
    }

    @GetMapping
    public ResponseEntity<List<Account>> getAllAccounts() {
        List<Account> accounts = accountService.getAllAccounts();
        return ResponseEntity.ok(accounts);
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<Account> getAccountById(@PathVariable int accountId) {
        return accountService.getAccountById(accountId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Account> createAccount(@RequestBody Account.Request request) {
        Account.Response response = accountService.createAccount(request);
        if (response.getReturnCode() != 0) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(response.getAccount());
    }

    @PutMapping("/{accountId}")
    public ResponseEntity<Account> updateAccount(@PathVariable int accountId, @RequestBody Account accountDetails) {
        // Update logic here
        return ResponseEntity.ok(updatedAccount);
    }

    @DeleteMapping("/{accountId}")
    public ResponseEntity<Void> deleteAccount(@PathVariable int accountId) {
        accountService.deleteAccountById(accountId);
        return ResponseEntity.ok().build();
    }

    // Deposit and withdraw endpoints here
}
