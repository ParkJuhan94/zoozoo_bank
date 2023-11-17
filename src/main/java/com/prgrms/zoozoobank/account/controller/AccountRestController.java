package com.prgrms.zoozoobank.account.controller;

import com.prgrms.zoozoobank.account.domain.Account;
import com.prgrms.zoozoobank.account.service.AccountService;
import com.prgrms.zoozoobank.bankbranch.domain.BankBranch;
import com.prgrms.zoozoobank.bankbranch.service.BankBranchService;
import com.prgrms.zoozoobank.customer.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

import static com.prgrms.zoozoobank.account.controller.AccountMessage.*;
import static com.prgrms.zoozoobank.account.controller.AccountMessage.SUCCESS_CREATE_ACCOUNT;

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
        if (accounts.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(accounts, HttpStatus.OK);
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<Account> getAccountById(@PathVariable("accountId") int accountId) {
        Account account = accountService.getAccountById(accountId).getAccount();
        return new ResponseEntity<>(account, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createAccount(@RequestBody Account.Request request) {
        Account.Response response = accountService.createAccount(request);
        if (response.getReturnCode() != 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(FAILURE_CREATE_ACCOUNT.getMessage());
        }
        return ResponseEntity.ok().body(SUCCESS_CREATE_ACCOUNT.getMessage());
    }

    @DeleteMapping("/{accountId}")
    public ResponseEntity<Void> deleteAccount(@PathVariable("accountId") int accountId) {
        accountService.deleteAccountById(accountId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{accountId}")
    @Transactional
    public ResponseEntity<?> updateBalance(@PathVariable("accountId") int accountId, @RequestParam("amount") long amount) {
        Account.Response accountResponse = accountService.modifyAccountBalance(accountId, amount);
        int branchId = accountService.getAccountById(accountId).getAccount().getBranchId();
        BankBranch.Response bankBranchResponse = bankBranchService.modifyBankBranchAssets(branchId, amount);

        if (accountResponse.getReturnCode() == 1 && bankBranchResponse.getReturnCode() == 1) {
            return ResponseEntity.ok().body(SUCCESS_UPDATE_ACCOUNT.getMessage());
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(FAILURE_UPDATE_ACCOUNT.getMessage());
        }
    }

}
