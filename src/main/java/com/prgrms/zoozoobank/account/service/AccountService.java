package com.prgrms.zoozoobank.account.service;

import com.prgrms.zoozoobank.account.domain.Account;
import com.prgrms.zoozoobank.account.repository.AccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class AccountService {
    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account.Response createAccount(Account.Request request) {
        if(accountRepository.save(request)){
            return new Account.Response(null, 1);
        }
        return new Account.Response(null, 0);
    }

    public Account.Response getAccountById(int id) {
        Optional<Account> accountOptional = accountRepository.findById(id);
        if (accountOptional.isPresent()) {
            return new Account.Response(accountOptional.get(), 1);
        } else {
            return new Account.Response(null, 0);
        }
    }

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    public void deleteAccountById(int accountId) {
        accountRepository.deleteById(accountId);
    }

    public Account.Response modifyAccountBalance(int id, long amount) {
        boolean modifyAccount = accountRepository.updateBalance(id, amount);

        if(modifyAccount){
            return new Account.Response(null, 1);
        }else{
            return new Account.Response(null, 0);
        }
    }
}
