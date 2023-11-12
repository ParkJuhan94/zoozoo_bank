package com.prgrms.zoozoobank.account;

import com.prgrms.zoozoobank.bankbranch.BankBranch;
import com.prgrms.zoozoobank.bankbranch.BankBranchService;
import com.prgrms.zoozoobank.customer.Customer;
import com.prgrms.zoozoobank.customer.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.prgrms.zoozoobank.account.AccountMessage.*;
import static com.prgrms.zoozoobank.account.AccountValidator.*;

@Service
@Slf4j
public class AccountService {

    private final AccountRepository accountRepository;
    private final CustomerService customerService;
    private final BankBranchService bankBranchService;

    public AccountService(AccountRepository accountRepository, CustomerService customerService, BankBranchService bankBranchService) {
        this.accountRepository = accountRepository;
        this.customerService = customerService;
        this.bankBranchService = bankBranchService;
    }

    public Account.Response createAccount(Account.Request request) {
//        Account.Response validationResponse = validateCreateRequest(request);
//        if (validationResponse != null) {
//            return validationResponse; // Validation failed
//        }

        Account.Response repositoryResponse = accountRepository.save(request);
        return handleRepositoryResponse(
                repositoryResponse,
                SUCCESS_CREATE_ACCOUNT.getMessage(),
                FAILURE_CREATE_ACCOUNT.getMessage()
        );
    }

    public List<Account.Info> getAllAccounts() {
        return accountRepository.findAll();
    }

    public void deleteAccountById(int accountId) {
        accountRepository.deleteById(accountId);
    }
}
