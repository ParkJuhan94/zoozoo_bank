package com.prgrms.zoozoobank.account;

import com.prgrms.zoozoobank.bankbranch.BankBranch;
import com.prgrms.zoozoobank.bankbranch.BankBranchService;
import com.prgrms.zoozoobank.customer.Customer;
import com.prgrms.zoozoobank.customer.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@Slf4j
@RequestMapping("/account")
public class AccountController {

    private final AccountService accountService;
    private final CustomerService customerService;
    private final BankBranchService bankBranchService;


    public AccountController(AccountService accountService, CustomerService customerService, BankBranchService bankBranchService) {
        this.accountService = accountService;
        this.customerService = customerService;
        this.bankBranchService = bankBranchService;
    }

    @GetMapping("/all")
    public String getAllAccounts(Model model) {
        List<Account.Info> accounts = accountService.getAllAccounts();
        List<String> customerNames = new ArrayList<>();
        List<String> customerContactInfos = new ArrayList<>();
        List<String> branchNames = new ArrayList<>();

        for (Account.Info account : accounts) {
            Customer.Info customer = customerService.getCustomerById(account.getCustomerId()).getInfo();
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

    @PostMapping("/delete/{accountId}")
    public String deleteAccount(@PathVariable int accountId) {
        accountService.deleteAccountById(accountId);
        return "redirect:/account/all";
    }

    @GetMapping("/create")
    public String showAccountCreationForm(Model model) {
        List<Customer.Info> customers = customerService.getAllCustomers();
        model.addAttribute("customers", customers);
        return "account-create-step1";
    }

    @PostMapping("/create/step2")
    public String showBranchSelection(@RequestParam("customerId") int customerId, Model model) {
        List<BankBranch.Info> branches = bankBranchService.getAllBankBranches();
        Customer.Info customer = customerService.getCustomerById(customerId).getInfo();
        model.addAttribute("branches", branches);
        model.addAttribute("selectedCustomer", customer);
        return "account-create-step2";
    }

    @PostMapping("/create/complete")
    public String completeAccountCreation(@ModelAttribute Account.Request form,
                                          RedirectAttributes redirectAttributes) {
        boolean isCreated = false;
        if(accountService.createAccount(form).getReturnCode() == 1){
            isCreated = true;
        }

        if (isCreated) {
            redirectAttributes.addFlashAttribute("createMessage", "계좌가 성공적으로 개설되었습니다.");
        } else {
            redirectAttributes.addFlashAttribute("createMessage", "계좌 개설에 실패했습니다.");
        }
        return "redirect:/account/all";
    }
}
