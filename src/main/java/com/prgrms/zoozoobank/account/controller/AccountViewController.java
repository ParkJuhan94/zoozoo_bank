package com.prgrms.zoozoobank.account.controller;

import com.prgrms.zoozoobank.account.service.AccountService;
import com.prgrms.zoozoobank.account.domain.Account;
import com.prgrms.zoozoobank.bankbranch.domain.BankBranch;
import com.prgrms.zoozoobank.bankbranch.service.BankBranchService;
import com.prgrms.zoozoobank.customer.domain.Customer;
import com.prgrms.zoozoobank.customer.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

import static com.prgrms.zoozoobank.account.controller.AccountMessage.*;

@Controller
@Slf4j
@RequestMapping("/account")
public class AccountViewController {

    private final AccountService accountService;
    private final CustomerService customerService;
    private final BankBranchService bankBranchService;
    private final String UPDATE_MESSAGE = "updatemessage";
    private final String REDIRECT_ACCOUNT_ALL = "redirect:/account/all";

    public AccountViewController(AccountService accountService, CustomerService customerService, BankBranchService bankBranchService) {
        this.accountService = accountService;
        this.customerService = customerService;
        this.bankBranchService = bankBranchService;
    }

    @GetMapping("/all")
    public String getAllAccounts(Model model) {
        List<Account> accounts = accountService.getAllAccounts();
        List<String> customerNames = new ArrayList<>();
        List<String> customerContactInfos = new ArrayList<>();
        List<String> branchNames = new ArrayList<>();

        for (Account account : accounts) {
            Customer customer = customerService.getCustomerById(account.getCustomerId()).getCustomer();
            customerNames.add(customer.getName());
            customerContactInfos.add(customer.getContactInfo());

            BankBranch branch = bankBranchService.getBankBranchById(account.getBranchId()).getBankBranch();
            branchNames.add(branch.getBranchName());
        }

        model.addAttribute("accounts", accounts);
        model.addAttribute("customerNames", customerNames);
        model.addAttribute("customerContactInfos", customerContactInfos);
        model.addAttribute("branchNames", branchNames);

        return "account-list";
    }

    @PostMapping("/delete/{accountId}")
    public String deleteAccount(@PathVariable int accountId) {
        accountService.deleteAccountById(accountId);
        return REDIRECT_ACCOUNT_ALL;
    }

    @GetMapping("/create/step1")
    public String showAccountCreationForm(Model model) {
        List<Customer> customers = customerService.getAllCustomers();
        model.addAttribute("customers", customers);
        return "account-create-step1";
    }

    @PostMapping("/create/step2")
    public String showBranchSelection(@RequestParam("customerId") int customerId, Model model) {
        List<BankBranch> branches = bankBranchService.getAllBankBranches();
        Customer customer = customerService.getCustomerById(customerId).getCustomer();

        model.addAttribute("branches", branches);
        model.addAttribute("selectedCustomer", customer);
        return "account-create-step2";
    }

    @PostMapping("/create/complete")
    public String completeAccountCreation(@ModelAttribute Account.Request form, RedirectAttributes redirectAttributes) {
        Account.Response response = accountService.createAccount(form);
        if(response.getReturnCode() == 0){
            redirectAttributes.addFlashAttribute("createMessage", FAILURE_CREATE_ACCOUNT.getMessage());
        }
        redirectAttributes.addFlashAttribute("createMessage", SUCCESS_CREATE_ACCOUNT.getMessage());

        return REDIRECT_ACCOUNT_ALL;
    }

    @GetMapping("/deposit/{accountId}")
    public String showDepositForm(@PathVariable int accountId, Model model) {
        Account account = accountService.getAccountById(accountId).getAccount();
        model.addAttribute("account", account);
        return "deposit";
    }

    @PostMapping("/deposit/{accountId}")
    @Transactional
    public String processDeposit(@PathVariable int accountId, @RequestParam long amount, RedirectAttributes redirectAttributes) {
        Account.Response accountResponse = accountService.modifyAccountBalance(accountId, amount);
        int branchId = accountService.getAccountById(accountId).getAccount().getBranchId();
        BankBranch.Response bankBranchResponse = bankBranchService.modifyBankBranchAssets(branchId, amount);

        if (accountResponse.getReturnCode() == 1 && bankBranchResponse.getReturnCode() == 1) {
            redirectAttributes.addFlashAttribute(UPDATE_MESSAGE, SUCCESS_UPDATE_ACCOUNT.getMessage());
        } else {
            redirectAttributes.addFlashAttribute(UPDATE_MESSAGE, FAILURE_UPDATE_ACCOUNT.getMessage());
        }
        return REDIRECT_ACCOUNT_ALL;
    }

    @GetMapping("/withdraw/{accountId}")
    public String showWithdrawForm(@PathVariable int accountId, Model model) {
        Account account = accountService.getAccountById(accountId).getAccount();
        model.addAttribute("account", account);
        return "withdraw";
    }

    @PostMapping("/withdraw/{accountId}")
    @Transactional
    public String processWithdraw(@PathVariable int accountId, @RequestParam long amount, RedirectAttributes redirectAttributes) {
        Account.Response accountResponse = accountService.modifyAccountBalance(accountId, (-1) * amount);
        int branchId = accountService.getAccountById(accountId).getAccount().getBranchId();
        BankBranch.Response bankBranchResponse = bankBranchService.modifyBankBranchAssets(branchId, (-1) * amount);

        if (accountResponse.getReturnCode() == 1 && bankBranchResponse.getReturnCode() == 1) {
            redirectAttributes.addFlashAttribute(UPDATE_MESSAGE, SUCCESS_UPDATE_ACCOUNT.getMessage());
        } else {
            redirectAttributes.addFlashAttribute(UPDATE_MESSAGE, FAILURE_UPDATE_ACCOUNT.getMessage());
        }
        return REDIRECT_ACCOUNT_ALL;
    }
}
