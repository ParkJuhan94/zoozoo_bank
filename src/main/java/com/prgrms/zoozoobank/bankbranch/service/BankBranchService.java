package com.prgrms.zoozoobank.bankbranch.service;

import com.prgrms.zoozoobank.bankbranch.domain.BankBranch;
import com.prgrms.zoozoobank.bankbranch.repository.BankBranchRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class BankBranchService {

    private final BankBranchRepository bankBranchRepository;

    public BankBranchService(BankBranchRepository bankBranchRepository) {
        this.bankBranchRepository = bankBranchRepository;
    }

    public BankBranch.Response getBankBranchById(int id) {
        Optional<BankBranch> branchOptional = bankBranchRepository.findById(id);

        if (branchOptional.isPresent()) {
            return new BankBranch.Response(branchOptional.get(), 1);
        } else {
            return new BankBranch.Response(null, 0);
        }
    }

    public BankBranch.Response createBankBranch(BankBranch.Request request) {
        if(validateDuplicatedBranch(request.getBranchName())){
            return new BankBranch.Response(null, 0);
        }

        if(bankBranchRepository.save(request)){
            return new BankBranch.Response(null, 1);
        }else{
            return new BankBranch.Response(null, 0);
        }
    }

    public List<BankBranch> getAllBankBranches() {
        return bankBranchRepository.findAll();
    }

    public void deleteBankBranchById(int branchId) {
        bankBranchRepository.deleteById(branchId);
    }

    public BankBranch.Response modifyBankBranchAssets(int branchId, long amount) {
        boolean modifyBranchAssets = bankBranchRepository.updateAssets(branchId, amount);

        if(modifyBranchAssets){
            return new BankBranch.Response(null, 1);
        }else{
            return new BankBranch.Response(null, 0);
        }
    }

    private boolean validateDuplicatedBranch(String branchName) {
        if (bankBranchRepository.bankBranchExists(branchName)) {
            return true;
        }
        return false;
    }
}
