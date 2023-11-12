package com.prgrms.zoozoobank.customer;

import lombok.extern.slf4j.Slf4j;

import static com.prgrms.zoozoobank.customer.Customer.*;
import static com.prgrms.zoozoobank.customer.CustomerMessage.CONTACT_INFO_IS_NULL;
import static com.prgrms.zoozoobank.customer.CustomerMessage.CUSTOMER_NAME_IS_NULL;

@Slf4j
public class CustomerValidator {

    public static Response validateCreateRequest(Request request) {
        if (request.getName() == null || request.getName().isEmpty()) {
            log.info(CUSTOMER_NAME_IS_NULL.getMessage());
            return new Response(null, 0, CUSTOMER_NAME_IS_NULL.getMessage());
        }

        if (request.getContactInfo() == null || request.getContactInfo().isEmpty()) {
            log.info(CONTACT_INFO_IS_NULL.getMessage());
            return new Response(null, 0, CONTACT_INFO_IS_NULL.getMessage());
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
