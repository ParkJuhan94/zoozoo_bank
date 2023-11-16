package com.prgrms.zoozoobank.bankbranch.controller;

import com.prgrms.zoozoobank.bankbranch.service.BankBranchService;
import com.prgrms.zoozoobank.bankbranch.domain.BankBranch;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

import static com.prgrms.zoozoobank.bankbranch.controller.BankBranchMessage.*;

@Controller
@Slf4j
@RequestMapping("/bankbranch")
public class BankBranchViewController {

    private final BankBranchService bankBranchService;

    public BankBranchViewController(BankBranchService bankBranchService) {
        this.bankBranchService = bankBranchService;
    }

    @GetMapping("/{branchId}")
    public ResponseEntity<BankBranch.Response> getBankBranchById(@PathVariable int branchId) {
        BankBranch.Response response = bankBranchService.getBankBranchById(branchId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/all")
    public String getAllBankBranches(Model model) {
        List<BankBranch> branches = bankBranchService.getAllBankBranches();
        model.addAttribute("branches", branches);
        return "bankbranch-list";
    }

    @GetMapping("/create")
    public String showCreateBankBranchForm() {
        return "bankbranch-create";
    }

    @PostMapping("/create")
    public String createBankBranch(@RequestParam(value = "assets") long assets,
                                   @RequestParam(value = "branchName") String branchName,
                                   RedirectAttributes redirectAttributes) {
        if(!validateCreateRequest(assets, branchName)){
            redirectAttributes.addFlashAttribute("errorMessage", FAILURE_CREATE_BRANCH.getMessage());
            return "redirect:/bankbranch/create";
        }

        bankBranchService.createBankBranch(new BankBranch.Request(assets, branchName));
        redirectAttributes.addFlashAttribute("createMessage", SUCCESS_CREATE_BRANCH.getMessage());
        return "redirect:/bankbranch/all";
    }

    @PostMapping("/delete/{branchId}")
    public String deleteBankBranch(@PathVariable int branchId) {
        bankBranchService.deleteBankBranchById(branchId);
        return "redirect:/bankbranch/all";
    }

    private boolean validateCreateRequest(long assets, String branchName) {
        if (assets < 0 || branchName == null || branchName.isEmpty()) {
            return false;
        }
        return true;
    }
}
