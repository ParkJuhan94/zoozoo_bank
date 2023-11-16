package com.prgrms.zoozoobank.bankbranch.repository;

import com.prgrms.zoozoobank.bankbranch.domain.BankBranch;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.*;

import static com.prgrms.zoozoobank.bankbranch.controller.BankBranchMessage.SUCCESS_CREATE_BRANCH;

@Repository
public class BankBranchRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public BankBranchRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public Optional<BankBranch> findById(int id) {
        String sql = "SELECT id, assets, branch_name FROM bankbranch WHERE id = :id";

        Map<String, Object> params = new HashMap<>();
        params.put("id", id);

        List<BankBranch> resultList = namedParameterJdbcTemplate.query(sql, params, rs -> {
            List<BankBranch> branches = new ArrayList<>();

            while (rs.next()) {
                BankBranch bankBranch = new BankBranch();
                bankBranch.setId(rs.getInt("id"));
                bankBranch.setAssets(rs.getLong("assets"));
                bankBranch.setBranchName(rs.getString("branch_name"));
                branches.add(bankBranch);
            }

            return branches;
        });

        return resultList.isEmpty() ? Optional.empty() : Optional.of(resultList.get(0));
    }

    public boolean save(BankBranch.Request request) {
        String sql = "INSERT INTO bankbranch (assets, branch_name) VALUES (:assets, :branchName)";
        Map<String, Object> params = new HashMap<>();
        params.put("assets", request.getAssets());
        params.put("branchName", request.getBranchName());

        int update = namedParameterJdbcTemplate.update(sql, params);
        if(update == 0){
            return false;
        }
        return true;
    }

    public List<BankBranch> findAll() {
        String sql = "SELECT * FROM bankbranch";
        return namedParameterJdbcTemplate.query(sql, new BeanPropertyRowMapper<>(BankBranch.class));
    }

    public void deleteById(int branchId) {
        String sql = "DELETE FROM bankbranch WHERE id = :id";
        Map<String, Object> params = new HashMap<>();
        params.put("id", branchId);
        namedParameterJdbcTemplate.update(sql, params);
    }

    public boolean updateAssets(int branchId, long amount) {
        Optional<BankBranch> bankBranchInfo = findById(branchId);
        if (!bankBranchInfo.isPresent()) {
            return false;
        }

        long newAssets = bankBranchInfo.get().getAssets() + amount;
        if (newAssets < 0) {
            return false;
        }

        String sql = "UPDATE bankbranch SET assets = :assets WHERE id = :id";
        Map<String, Object> params = new HashMap<>();
        params.put("id", branchId);
        params.put("assets", newAssets);

        int update = namedParameterJdbcTemplate.update(sql, params);
        if (update == 0) {
            return false;
        }
        return true;
    }

    public boolean bankBranchExists(String branchName) {
        String sql = "SELECT COUNT(*) FROM bankbranch WHERE branch_name = :branchName";
        Map<String, Object> params = new HashMap<>();
        params.put("branchName", branchName);

        Integer count = namedParameterJdbcTemplate.queryForObject(sql, params, Integer.class);
        return count != null && count > 0;
    }
}
