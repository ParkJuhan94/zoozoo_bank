package com.prgrms.zoozoobank.bankbranch;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.prgrms.zoozoobank.bankbranch.BankBranchMessage.FAILURE_CREATE_BRANCH;
import static com.prgrms.zoozoobank.bankbranch.BankBranchMessage.SUCCESS_CREATE_BRANCH;

@Repository
public class BankBranchRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public BankBranchRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public Optional<BankBranch.Info> findById(int id) {
        String sql = "SELECT id, assets, branch_name FROM bankbranch WHERE id = :id";

        Map<String, Object> params = new HashMap<>();
        params.put("id", id);

        return namedParameterJdbcTemplate.query(sql, params, rs -> {
            BankBranch.Info branchInfo = new BankBranch.Info();

            branchInfo.setId(rs.getInt("id"));
            branchInfo.setAssets(rs.getLong("assets"));
            branchInfo.setBranchName(rs.getString("branch_name"));

            return Optional.of(branchInfo);
        }).stream().findFirst();
    }

    public BankBranch.Response save(BankBranch.Request request) {
        if (branchExists(request.getBranchName())) {
            return new BankBranch.Response(null, 0, FAILURE_CREATE_BRANCH.getMessage());
        }

        String sql = "INSERT INTO bankbranch (assets, branch_name) VALUES (:assets, :branchName)";
        Map<String, Object> params = new HashMap<>();
        params.put("assets", request.getAssets());
        params.put("branchName", request.getBranchName());

        namedParameterJdbcTemplate.update(sql, params);

        return new BankBranch.Response(new BankBranch.Info(0, request.getAssets(), request.getBranchName(), null),
                1, SUCCESS_CREATE_BRANCH.getMessage());
    }

    public List<BankBranch.Info> findAll() {
        String sql = "SELECT * FROM bankbranch";
        return namedParameterJdbcTemplate.query(sql, new BeanPropertyRowMapper<>(BankBranch.Info.class));
    }

    public void deleteById(int branchId) {
        String sql = "DELETE FROM bankbranch WHERE id = :id";
        Map<String, Object> params = new HashMap<>();
        params.put("id", branchId);
        namedParameterJdbcTemplate.update(sql, params);

        // You may need to handle the deletion of associated accounts if needed
    }

    private boolean branchExists(String branchName) {
        String sql = "SELECT COUNT(*) FROM bankbranch WHERE branch_name = :branchName";
        Map<String, Object> params = new HashMap<>();
        params.put("branchName", branchName);

        Integer count = namedParameterJdbcTemplate.queryForObject(sql, params, Integer.class);
        return count != null && count > 0;
    }
}
