package com.prgrms.zoozoobank.account;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

public class Account {

    @Getter
    @AllArgsConstructor
    @Builder
    public static class Info {
        private int id;
        private long balance;
        private int customerId;
        private int branchId;
    }

    @Getter @Setter
    public static class Request {
        private long balance;
        private int customerId;
        private int branchId;
    }

    @Getter
    @AllArgsConstructor
    public static class Response {
        private Account.Info info;
        private int returnCode;
        private String returnMessage;
    }
}
