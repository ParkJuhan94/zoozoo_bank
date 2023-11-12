package com.prgrms.zoozoobank.bankbranch;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@Slf4j
@RequestMapping("/bankbranch")
public class BankBranchController {

    private final BankBranchService bankBranchService;

    public BankBranchController(BankBranchService bankBranchService) {
        this.bankBranchService = bankBranchService;
    }

    @GetMapping("/{branchId}")
    public ResponseEntity<BankBranch.Response> getBankBranchById(@PathVariable int branchId) {
        BankBranch.Response response = bankBranchService.findById(branchId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/all")
    public String getAllBankBranches(Model model) {
        List<BankBranch.Info> branches = bankBranchService.getAllBankBranches();
        model.addAttribute("branches", branches);
        return "bankbranch-list";
    }

    @GetMapping("/create")
    public String showCreateBankBranchForm() {
        return "bankbranch-create";
    }

    @PostMapping("/create")
    public String createBankBranch(@RequestParam(value = "assets") long assets,
                                   @RequestParam(value = "branchName") String branchName) {
        BankBranch.Response response = bankBranchService.createBankBranch(new BankBranch.Request(assets, branchName));
        return "redirect:/bankbranch/all";
    }

    @PostMapping("/delete/{branchId}")
    public String deleteBankBranch(@PathVariable int branchId) {
        bankBranchService.deleteBankBranchById(branchId);
        return "redirect:/bankbranch/all";
    }
}
