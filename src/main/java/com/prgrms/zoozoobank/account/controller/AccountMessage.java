package com.prgrms.zoozoobank.account.controller;

import lombok.Getter;

@Getter
public enum AccountMessage {
    SUCCESS_FIND_ACCOUNT_BY_ID("To find a account by id is success"),
    FAILURE_FIND_ACCOUNT_BY_ID("Failed to find a account"),
    SUCCESS_CREATE_ACCOUNT("A account was created successfully"),
    FAILURE_CREATE_ACCOUNT("Failed to create a account"),
    SUCCESS_UPDATE_ACCOUNT("A account updated successfully"),
    FAILURE_UPDATE_ACCOUNT("A account can't updated"),
    ACCOUNT_NAME_IS_NULL("Account name is required"),
    BALANCE_IS_NULL("Balance is required");

    AccountMessage(String message) {
        this.message = message;
    }

    private final String message;
}
