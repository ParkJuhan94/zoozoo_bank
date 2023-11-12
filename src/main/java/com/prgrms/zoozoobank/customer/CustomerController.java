package com.prgrms.zoozoobank.customer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@Slf4j
@RequestMapping("/customer")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<Customer.Response> getCustomerById(@PathVariable int customerId) {
        Customer.Response response = customerService.findById(customerId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/all")
    public String getAllCustomers(Model model) {
        List<Customer.Info> customers = customerService.getAllCustomers();
        model.addAttribute("customers", customers);
        return "customer-list";
    }

    @GetMapping("/create")
    public String showCreateCustomerForm() {
        return "customer-create";
    }

    @PostMapping("/create")
    public String createCustomer(@RequestParam(value = "name") String name,
                                 @RequestParam(value = "contactInfo") String contactInfo) {
        Customer.Response response = customerService.createCustomer(new Customer.Request(name, contactInfo));
        return "redirect:/customer/all";
    }

    @PostMapping("/delete/{customerId}")
    public String deleteCustomer(@PathVariable int customerId) {
        customerService.deleteCustomerById(customerId);
        return "redirect:/customer/all";
    }

}
