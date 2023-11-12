package com.prgrms.zoozoobank.bankbranch;

import lombok.Getter;

@Getter
public enum BankBranchMessage {
    SUCCESS_FIND_BRANCH_BY_ID("To find a bank branch by id is success"),
    FAILURE_FIND_BRANCH_BY_ID("Failed to find a bank branch"),
    SUCCESS_CREATE_BRANCH("Bank branch was created successfully"),
    FAILURE_CREATE_BRANCH("Failed to create a bank branch"),
    BRANCH_NAME_IS_NULL("Branch name is required"),
    ASSETS_IS_NULL("Assets is required");

    BankBranchMessage(String message) {
        this.message = message;
    }

    private final String message;
}
