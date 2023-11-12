package com.prgrms.zoozoobank.bankbranch;

import com.prgrms.zoozoobank.account.Account;
import lombok.*;

import java.util.List;
import java.util.Optional;

public class BankBranch {

    @Getter @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Info {
        private int id;
        private long assets;
        private String branchName;
        private List<Account> accounts;
    }

    @Getter @Setter
    @AllArgsConstructor
    public static class Request {
        private long assets;
        private String branchName;
    }

    @Getter @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Response {
        private BankBranch.Info info;
        private int returnCode;
        private String returnMessage;
    }
}
