package com.prgrms.zoozoobank.customer.controller;

import lombok.Getter;

@Getter
public enum CustomerMessage {
    SUCCESS_FIND_CUSTOMER_BY_ID("To find a customer by id is success"),
    FAILURE_FIND_CUSTOMER_BY_ID("Failed to find a customer"),
    SUCCESS_CREATE_CUSTOMER("Customer was created successfully"),
    FAILURE_CREATE_CUSTOMER("Failed to create a customer"),
    FAILURE_CREATE_CUSTOMER_INPUT("Failed to create a customer because of wrong inputs"),
    FAILURE_CREATE_CUSTOMER_DUPLICATED("Failed to create a customer because he is duplicated"),;

    CustomerMessage(String message) {
        this.message = message;
    }

    private final String message;
}
