package com.prgrms.zoozoobank.account.domain;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Account {

    private int id;
    private long balance;
    private int customerId;
    private int branchId;

    @Override
    public String toString() {
        return "Info{" +
                "id=" + id +
                ", balance=" + balance +
                ", customerId=" + customerId +
                ", branchId=" + branchId +
                '}';
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class Request {
        private long balance;
        private int customerId;
        private int branchId;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Response {
        private Account account;
        private int returnCode;
    }
}
