package com.prgrms.zoozoobank.bankbranch;

import com.prgrms.zoozoobank.account.Account;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class BankBranch {

    @Getter
    @AllArgsConstructor
    @Builder
    public static class Info {
        private int id;
        private long assets;
        private String branchName;
        private List<Account> accounts;
    }

    @Getter @Setter
    public static class Request {
        private long assets;
        private String branchName;
        private List<Account> accounts;
    }

    @Getter
    @AllArgsConstructor
    public static class Response {
        private BankBranch.Info info;
        private int returnCode;
        private String returnMessage;
    }
}
