package com.prgrms.zoozoobank.bankbranch.controller;

import lombok.Getter;

@Getter
public enum BankBranchMessage {
    SUCCESS_FIND_BRANCH_BY_ID("To find a bank branch by id is success"),
    FAILURE_FIND_BRANCH_BY_ID("Failed to find a bank branch"),
    SUCCESS_CREATE_BRANCH("A bank branch was created successfully"),
    FAILURE_CREATE_BRANCH("Failed to create a bank branch"),
    SUCCESS_UPDATE_BRANCH("A bank branch updated successfully"),
    FAILURE_UPDATE_BRANCH("A bank branch can't updated"),
    BRANCH_NAME_IS_NULL("Bank branch name is required"),
    ASSETS_IS_NULL("Assets is required");

    BankBranchMessage(String message) {
        this.message = message;
    }

    private final String message;
}
