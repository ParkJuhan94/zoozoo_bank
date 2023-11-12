package com.prgrms.zoozoobank.account;

import com.prgrms.zoozoobank.bankbranch.BankBranch;
import com.prgrms.zoozoobank.bankbranch.BankBranchService;
import com.prgrms.zoozoobank.customer.Customer;
import com.prgrms.zoozoobank.customer.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@Slf4j
@RequestMapping("/account")
public class AccountController {

    private final AccountService accountService;
    private final CustomerService customerService; // Inject CustomerService
    private final BankBranchService bankBranchService; // Inject BankBranchService


    public AccountController(AccountService accountService, CustomerService customerService, BankBranchService bankBranchService) {
        this.accountService = accountService;
        this.customerService = customerService;
        this.bankBranchService = bankBranchService;
    }

//    @GetMapping("/{accountId}")
//    public ResponseEntity<Account.Response> getAccountById(@PathVariable int accountId) {
//        Account.Response response = accountService.findById(accountId);
//        return new ResponseEntity<>(response, HttpStatus.OK);
//    }

    @GetMapping("/all")
    public String getAllAccounts(Model model) {
        List<Account.Info> accounts = accountService.getAllAccounts();
        List<String> customerNames = new ArrayList<>();
        List<String> customerContactInfos = new ArrayList<>();
        List<String> branchNames = new ArrayList<>();

        for (Account.Info account : accounts) {
            Customer.Info customer = customerService.findById(account.getCustomerId()).getInfo();
            customerNames.add(customer.getName());
            customerContactInfos.add(customer.getContactInfo());

            BankBranch.Info branch = bankBranchService.findById(account.getBranchId()).getInfo();
            branchNames.add(branch.getBranchName());
        }

        model.addAttribute("accounts", accounts);
        model.addAttribute("customerNames", customerNames);
        model.addAttribute("customerContactInfos", customerContactInfos);
        model.addAttribute("branchNames", branchNames);

        return "account-list";
    }
//
//    @GetMapping("/create")
//    public String showCreateAccountForm(Model model) {
//        // You may need to retrieve the list of customers and branches for user selection
//        List<Customer.Info> customers = accountService.getAllCustomers();
//        List<BankBranch.Info> branches = accountService.getAllBankBranches();
//
//        model.addAttribute("customers", customers);
//        model.addAttribute("branches", branches);
//
//        return "account-create";
//    }
//
//    @PostMapping("/create")
//    public String createAccount(@RequestParam(value = "balance") long balance,
//                                @RequestParam(value = "customerId") int customerId,
//                                @RequestParam(value = "branchId") int branchId) {
//        Account.Response response = accountService.createAccount(new Account.Request(balance, customerId, branchId));
//        return "redirect:/account/all";
//    }

    @PostMapping("/delete/{accountId}")
    public String deleteAccount(@PathVariable int accountId) {
        accountService.deleteAccountById(accountId);
        return "redirect:/account/all";
    }
}
