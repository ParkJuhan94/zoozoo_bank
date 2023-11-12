package com.prgrms.zoozoobank.customer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.prgrms.zoozoobank.customer.CustomerMessage.*;
import static com.prgrms.zoozoobank.customer.CustomerValidator.*;

@Service
@Slf4j
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer.Response findById(int id) {
        Optional<Customer.Info> customerOptional = customerRepository.findById(id);
        Customer.Response repositoryResponse = new Customer.Response();

        if (customerOptional.isPresent()) {
            Customer.Info customerInfo = customerOptional.get();
            repositoryResponse.setInfo(customerInfo);
            repositoryResponse.setReturnCode(1);
        } else {
            repositoryResponse.setReturnCode(0);
        }

        return handleRepositoryResponse(repositoryResponse, SUCCESS_FIND_CUSTOMER_BY_ID.getMessage(), FAILURE_FIND_CUSTOMER_BY_ID.getMessage());
    }

    public Customer.Response createCustomer(Customer.Request request) {
        Customer.Response validationResponse = validateCreateRequest(request);
        if (validationResponse != null) {
            return validationResponse; // Validation failed
        }

        Customer.Response repositoryResponse = customerRepository.save(request);
        return handleRepositoryResponse(repositoryResponse, SUCCESS_CREATE_CUSTOMER.getMessage(), FAILURE_CREATE_CUSTOMER.getMessage());
    }

    public List<Customer.Info> getAllCustomers() {
        return customerRepository.findAll();
    }

    public void deleteCustomerById(int customerId) {
        customerRepository.deleteById(customerId);
    }
}
