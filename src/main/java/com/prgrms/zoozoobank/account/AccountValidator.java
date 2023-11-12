package com.prgrms.zoozoobank.account;

import lombok.extern.slf4j.Slf4j;

import static com.prgrms.zoozoobank.account.Account.Response;

@Slf4j
public class AccountValidator {

    public static Response handleRepositoryResponse(Response repositoryResponse, String successMessage, String failureMessage) {
        if (repositoryResponse.getReturnCode() == 0) {
            log.info(failureMessage);
            return new Response(null, 0, failureMessage);
        }

        repositoryResponse.setReturnMessage(successMessage);
        return repositoryResponse;
    }
}
