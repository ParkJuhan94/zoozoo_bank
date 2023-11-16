package com.prgrms.zoozoobank.account.repository;

import com.prgrms.zoozoobank.account.domain.Account;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.prgrms.zoozoobank.account.controller.AccountMessage.*;

@Repository
public class AccountRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public AccountRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public boolean save(Account.Request request) {
        String sql = "INSERT INTO account (balance, customer_id, branch_id) VALUES (:balance, :customerId, :branchId)";

        Map<String, Object> params = new HashMap<>();
        params.put("balance", request.getBalance());
        params.put("customerId", request.getCustomerId());
        params.put("branchId", request.getBranchId());

        int update = namedParameterJdbcTemplate.update(sql, params);

        if(update == 0){
            return false;
        }
        return true;
    }

    public List<Account> findAll() {
        String sql = "SELECT * FROM account";
        return namedParameterJdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Account.class));
    }

    public void deleteById(int accountId) {
        String sql = "DELETE FROM account WHERE id = :id";
        Map<String, Object> params = new HashMap<>();
        params.put("id", accountId);
        namedParameterJdbcTemplate.update(sql, params);
    }

    public Optional<Account> findById(int id) {
        String sql = "SELECT id, balance, customer_id, branch_id FROM account WHERE id = :id";

        Map<String, Object> params = new HashMap<>();
        params.put("id", id);

        List<Account> accountInfos = namedParameterJdbcTemplate.query(sql, params, (rs, rowNum) -> {
            Account accountInfo = new Account();
            accountInfo.setId(rs.getInt("id"));
            accountInfo.setBalance(rs.getLong("balance"));
            accountInfo.setCustomerId(rs.getInt("customer_id"));
            accountInfo.setBranchId(rs.getInt("branch_id"));
            return accountInfo;
        });

        if (accountInfos.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(accountInfos.get(0));
        }
    }

    public boolean updateBalance(int accountId, long amount) {
        Optional<Account> accountInfo = findById(accountId);
        if (!accountInfo.isPresent()) {
            return false;
        }

        long newBalance = accountInfo.get().getBalance() + amount;
        if (newBalance < 0) {
            return false;
        }

        String sql = "UPDATE account SET balance = :balance WHERE id = :id";
        Map<String, Object> params = new HashMap<>();
        params.put("id", accountId);
        params.put("balance", newBalance);

        int update = namedParameterJdbcTemplate.update(sql, params);
        if (update == 0) {
            return false;
        }
        return true;
    }
}
