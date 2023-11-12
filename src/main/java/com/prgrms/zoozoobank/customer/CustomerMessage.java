package com.prgrms.zoozoobank.customer;

import lombok.Getter;

@Getter
public enum CustomerMessage {
    SUCCESS_FIND_CUSTOMER_BY_ID("To find a customer by id is success"),
    FAILURE_FIND_CUSTOMER_BY_ID("Failed to find a customer"),
    SUCCESS_CREATE_CUSTOMER("Customer created successfully"),
    FAILURE_CREATE_CUSTOMER("Failed to create customer"),
    CUSTOMER_NAME_IS_NULL("Name is required"),
    CONTACT_INFO_IS_NULL("Contact info is required");


    CustomerMessage(String message) {
        this.message = message;
    }

    private final String message;
}
