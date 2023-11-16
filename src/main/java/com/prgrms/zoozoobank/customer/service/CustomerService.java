package com.prgrms.zoozoobank.customer.service;

import com.prgrms.zoozoobank.customer.domain.Customer;
import com.prgrms.zoozoobank.customer.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer.Response getCustomerById(int id) {
        Optional<Customer> customerOptional = customerRepository.findById(id);
        if (customerOptional.isPresent()) {
            return new Customer.Response(customerOptional.get(), 1);
        } else {
            return new Customer.Response(null, 0);
        }
    }

    public Customer.Response createCustomer(Customer.Request request) {
        if (validateDuplicatedCustomer(request.getName(), request.getContactInfo())) {
            return new Customer.Response(null, 0);
        }

        if (customerRepository.save(request)) {
            return new Customer.Response(null, 1);
        } else {
            return new Customer.Response(null, 0);
        }
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public void deleteCustomerById(int customerId) {
        customerRepository.deleteById(customerId);
    }

    private boolean validateDuplicatedCustomer(String name, String contactInfo) {
        if (customerRepository.customerExists(name, contactInfo)) {
            return true;
        }
        return false;
    }
}
