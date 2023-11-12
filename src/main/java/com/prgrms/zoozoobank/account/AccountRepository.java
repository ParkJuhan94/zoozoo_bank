package com.prgrms.zoozoobank.account;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import static com.prgrms.zoozoobank.account.AccountMessage.FAILURE_CREATE_ACCOUNT;
//import static com.prgrms.zoozoobank.account.AccountMessage.SUCCESS_CREATE_ACCOUNT;

@Repository
public class AccountRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public AccountRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

//    public Account.Response save(Account.Request request) {
//        String sql = "INSERT INTO account (balance, customer_id, branch_id) VALUES (:balance, :customerId, :branchId)";
//
//        Map<String, Object> params = new HashMap<>();
//        params.put("balance", request.getBalance());
//        params.put("customerId", request.getCustomerId());
//        params.put("branchId", request.getBranchId());
//
//        namedParameterJdbcTemplate.update(sql, params);
//
//        // You may need to retrieve the generated ID if your database generates it
//        // int generatedId = namedParameterJdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", params, Integer.class);
//
//        return new Account.Response(
//                new Account.Info( /* Set the properties accordingly */ ),
//                1,
//                SUCCESS_CREATE_ACCOUNT.getMessage()
//        );
//    }

    public List<Account.Info> findAll() {
        String sql = "SELECT * FROM account";
        return namedParameterJdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Account.Info.class));
    }

    public void deleteById(int accountId) {
        String sql = "DELETE FROM account WHERE id = :id";
        Map<String, Object> params = new HashMap<>();
        params.put("id", accountId);
        namedParameterJdbcTemplate.update(sql, params);
    }
}
