package com.prgrms.zoozoobank.bankbranch;

import lombok.extern.slf4j.Slf4j;

import static com.prgrms.zoozoobank.bankbranch.BankBranch.Request;
import static com.prgrms.zoozoobank.bankbranch.BankBranch.Response;
import static com.prgrms.zoozoobank.bankbranch.BankBranchMessage.ASSETS_IS_NULL;
import static com.prgrms.zoozoobank.bankbranch.BankBranchMessage.BRANCH_NAME_IS_NULL;

@Slf4j
public class BankBranchValidator {

    public static Response validateCreateRequest(Request request) {
        if (request.getBranchName() == null || request.getBranchName().isEmpty()) {
            log.info(BRANCH_NAME_IS_NULL.getMessage());
            return new Response(null, 0, BRANCH_NAME_IS_NULL.getMessage());
        }
        return null;
    }

    public static Response handleRepositoryResponse(Response repositoryResponse, String successMessage, String failureMessage) {
        if (repositoryResponse.getReturnCode() == 0) {
            log.info(failureMessage);
            return new Response(null, 0, failureMessage);
        }

        repositoryResponse.setReturnMessage(successMessage);
        return repositoryResponse;
    }
}
