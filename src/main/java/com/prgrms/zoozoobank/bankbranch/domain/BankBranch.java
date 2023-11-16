package com.prgrms.zoozoobank.bankbranch.domain;

import com.prgrms.zoozoobank.account.domain.Account;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BankBranch {

    private int id;
    private long assets;
    private String branchName;
    private List<Account> accounts;

    @Getter
    @Setter
    @AllArgsConstructor
    public static class Request {
        private long assets;
        private String branchName;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Response {
        private BankBranch bankBranch;
        private int returnCode;
    }
}
