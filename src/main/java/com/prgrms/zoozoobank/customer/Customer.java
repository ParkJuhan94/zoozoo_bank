package com.prgrms.zoozoobank.customer;

import com.prgrms.zoozoobank.account.Account;
import lombok.*;

import java.util.List;

public class Customer {

    @Getter @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Info {
        private int id;
        private String name;
        private String contactInfo;
        private List<Account.Info> accounts;

        public Info(String name, String contactInfo) {
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
}

    @Getter @Setter
    @AllArgsConstructor
    public static class Request {
        private String name;
        private String contactInfo;
    }

    @Getter @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Response {
        private Info info;
        private int returnCode;
        private String returnMessage;
    }
}
