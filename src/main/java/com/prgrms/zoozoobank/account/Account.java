package com.prgrms.zoozoobank.account;

import lombok.*;

public class Account {

    /*
    Info 의 setter 가 없으니까 다음 메서드에서
    데이터가 전부 0으로 뽑힘 -> BeanPropertyRowMapper 가 setter 를 필요로 하는듯?

    public List<Account.Info> findAll() {
        String sql = "SELECT * FROM account";
        return namedParameterJdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Account.Info.class));
    }
     */
    @Getter @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Info {
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
    }

    @Getter @Setter
    @AllArgsConstructor
    public static class Request {
        private long balance;
        private int customerId;
        private int branchId;
    }

    @Getter @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Response {
        private Account.Info info;
        private int returnCode;
        private String returnMessage;
    }
}
