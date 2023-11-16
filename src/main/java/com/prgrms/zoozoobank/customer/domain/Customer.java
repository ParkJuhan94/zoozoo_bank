package com.prgrms.zoozoobank.customer.domain;

import com.prgrms.zoozoobank.account.domain.Account;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Customer {

    private int id;
    private String name;
    private String contactInfo;
    private List<Account> accounts;

    public Customer(String name, String contactInfo) {
        this.name = name;
        this.contactInfo = contactInfo;
    }

    @Override
    public String toString() {
        return "Info{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", contactInfo='" + contactInfo + '\'' +
                ", accounts=" + accounts +
                '}';
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class Request {
        private String name;
        private String contactInfo;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Response {
        private Customer customer;
        private int returnCode;
    }
}
