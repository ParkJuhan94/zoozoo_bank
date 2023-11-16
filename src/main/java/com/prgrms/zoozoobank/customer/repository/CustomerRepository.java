package com.prgrms.zoozoobank.customer.repository;

import com.prgrms.zoozoobank.customer.domain.Customer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.*;

import static com.prgrms.zoozoobank.customer.controller.CustomerMessage.FAILURE_CREATE_CUSTOMER;
import static com.prgrms.zoozoobank.customer.controller.CustomerMessage.SUCCESS_CREATE_CUSTOMER;

@Repository
@Slf4j
public class CustomerRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public CustomerRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public Optional<Customer> findById(int id) {
        String sql = "SELECT id, name, contact_info FROM customer WHERE id = :id";

        Map<String, Object> params = new HashMap<>();
        params.put("id", id);

        List<Customer> customerInfos = namedParameterJdbcTemplate.query(sql, params, (rs, rowNum) -> {
            Customer customerInfo = new Customer();
            customerInfo.setId(rs.getInt("id"));
            customerInfo.setName(rs.getString("name"));
            customerInfo.setContactInfo(rs.getString("contact_info"));
            return customerInfo;
        });

        if (customerInfos.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(customerInfos.get(0));
        }
    }

    public boolean save(Customer.Request request) {
        if (customerExists(request.getName(), request.getContactInfo())) {
            return false;
        }

        String sql = "INSERT INTO customer (name, contact_info) VALUES (:name, :contactInfo)";
        Map<String, Object> params = new HashMap<>();
        params.put("name", request.getName());
        params.put("contactInfo", request.getContactInfo());

        int update = namedParameterJdbcTemplate.update(sql, params);
        if(update == 0){
            return false;
        }
        return true;
    }

    public List<Customer> findAll() {
        String sql = "SELECT * FROM customer";
        return namedParameterJdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Customer.class));
    }

    public void deleteById(int customerId) {
        String sql = "DELETE FROM account WHERE customer_id = :id";
        Map<String, Object> params = new HashMap<>();
        params.put("id", customerId);
        namedParameterJdbcTemplate.update(sql, params);

        sql = "DELETE FROM customer WHERE id = :id";
        namedParameterJdbcTemplate.update(sql, params);
    }

    public boolean customerExists(String name, String contactInfo) {
        String sql = "SELECT COUNT(*) FROM customer WHERE contact_info = :contactInfo";
        Map<String, Object> params = new HashMap<>();
        params.put("name", name);
        params.put("contactInfo", contactInfo);

        Integer count = namedParameterJdbcTemplate.queryForObject(sql, params, Integer.class);
        return count != null && count > 0;
    }

}
