package com.prgrms.zoozoobank.bankbranch;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.prgrms.zoozoobank.bankbranch.BankBranchMessage.*;
import static com.prgrms.zoozoobank.bankbranch.BankBranchValidator.handleRepositoryResponse;
import static com.prgrms.zoozoobank.bankbranch.BankBranchValidator.validateCreateRequest;

@Service
@Slf4j
public class BankBranchService {

    private final BankBranchRepository bankBranchRepository;

    public BankBranchService(BankBranchRepository bankBranchRepository) {
        this.bankBranchRepository = bankBranchRepository;
    }

    public BankBranch.Response findById(int id) {
        Optional<BankBranch.Info> branchOptional = bankBranchRepository.findById(id);
        BankBranch.Response repositoryResponse = new BankBranch.Response();

        if (branchOptional.isPresent()) {
            BankBranch.Info branchInfo = branchOptional.get();
            repositoryResponse.setInfo(branchInfo);
            repositoryResponse.setReturnCode(1);
        } else {
            repositoryResponse.setReturnCode(0);
        }

        return handleRepositoryResponse(repositoryResponse, SUCCESS_FIND_BRANCH_BY_ID.getMessage(), FAILURE_FIND_BRANCH_BY_ID.getMessage());
    }

    public BankBranch.Response createBankBranch(BankBranch.Request request) {
        BankBranch.Response validationResponse = validateCreateRequest(request);
        if (validationResponse != null) {
            return validationResponse; // Validation failed
        }

        BankBranch.Response repositoryResponse = bankBranchRepository.save(request);
        return handleRepositoryResponse(repositoryResponse, SUCCESS_CREATE_BRANCH.getMessage(), FAILURE_CREATE_BRANCH.getMessage());
    }

    public List<BankBranch.Info> getAllBankBranches() {
        return bankBranchRepository.findAll();
    }

    public void deleteBankBranchById(int branchId) {
        bankBranchRepository.deleteById(branchId);
    }
}
