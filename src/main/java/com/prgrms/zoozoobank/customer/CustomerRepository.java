package com.prgrms.zoozoobank.customer;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.*;

import static com.prgrms.zoozoobank.customer.util.CustomerMessage.FAILURE_CREATE_CUSTOMER;
import static com.prgrms.zoozoobank.customer.util.CustomerMessage.SUCCESS_CREATE_CUSTOMER;

@Repository
public class CustomerRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public CustomerRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public Optional<Customer.Info> findById(int id) {
        String sql = "SELECT id, name, contact_info FROM customer WHERE id = :id";

        Map<String, Object> params = new HashMap<>();
        params.put("id", id);

        return namedParameterJdbcTemplate.query(sql, params, rs -> {
            Customer.Info customerInfo = new Customer.Info();

            customerInfo.setId(rs.getInt("id"));
            customerInfo.setName(rs.getString("name"));
            customerInfo.setContactInfo(rs.getString("contact_info"));

            return Optional.of(customerInfo);
        });
    }

    public Customer.Response save(Customer.Request request) {
        if (customerExists(request.getName(), request.getContactInfo())) {
            return new Customer.Response(null, 0, FAILURE_CREATE_CUSTOMER.getMessage());
        }

        String sql = "INSERT INTO customer (name, contact_info) VALUES (:name, :contactInfo)";
        Map<String, Object> params = new HashMap<>();
        params.put("name", request.getName());
        params.put("contactInfo", request.getContactInfo());

        namedParameterJdbcTemplate.update(sql, params);

        return new Customer.Response(new Customer.Info(request.getName(), request.getContactInfo()),
                1, SUCCESS_CREATE_CUSTOMER.getMessage());
    }

    public List<Customer.Info> findAll() {
        String sql = "SELECT * FROM customer";
        return namedParameterJdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Customer.Info.class));
    }

    public void deleteById(int customerId) {
        String sql = "DELETE FROM customer WHERE id = :id";
        Map<String, Object> params = new HashMap<>();
        params.put("id", customerId);
        namedParameterJdbcTemplate.update(sql, params);

        // 연결된 계좌 삭제
        sql = "DELETE FROM account WHERE customer_id = :id";
        namedParameterJdbcTemplate.update(sql, params);
    }

    private boolean customerExists(String name, String contactInfo) {
        String sql = "SELECT COUNT(*) FROM customer WHERE contact_info = :contactInfo";
        Map<String, Object> params = new HashMap<>();
        params.put("name", name);
        params.put("contactInfo", contactInfo);

        Integer count = namedParameterJdbcTemplate.queryForObject(sql, params, Integer.class);
        return count != null && count > 0;
    }

}
