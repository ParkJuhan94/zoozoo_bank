package com.prgrms.zoozoobank.customer.controller;

import com.prgrms.zoozoobank.customer.service.CustomerService;
import com.prgrms.zoozoobank.customer.domain.Customer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

import static com.prgrms.zoozoobank.customer.controller.CustomerMessage.*;

@Controller
@Slf4j
@RequestMapping("/customer")
public class CustomerViewController {

    private final CustomerService customerService;

    public CustomerViewController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<Customer.Response> getCustomerById(@PathVariable int customerId) {
        Customer.Response response = customerService.getCustomerById(customerId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/all")
    public String getAllCustomers(Model model) {
        List<Customer> customers = customerService.getAllCustomers();
        model.addAttribute("customers", customers);
        return "customer-list";
    }

    @GetMapping("/create")
    public String showCreateCustomerForm() {
        return "customer-create";
    }

    @PostMapping("/create")
    public String createCustomer(@RequestParam(value = "name") String name,
                                 @RequestParam(value = "contactInfo") String contactInfo,
                                 RedirectAttributes redirectAttributes) {
        Customer.Request request = new Customer.Request(name, contactInfo);

        if(!validateCreateRequest(request)){
            redirectAttributes.addFlashAttribute("errorMessage", FAILURE_CREATE_CUSTOMER_INPUT.getMessage());
            return "redirect:/customer/create";
        }

        if(customerService.createCustomer(request).getReturnCode() == 0){
            redirectAttributes.addFlashAttribute("errorMessage", FAILURE_CREATE_CUSTOMER_DUPLICATED.getMessage());
            return "redirect:/customer/create";
        }else{
            redirectAttributes.addFlashAttribute("createMessage", SUCCESS_CREATE_CUSTOMER.getMessage());
            return "redirect:/customer/all";
        }
    }

    @PostMapping("/delete/{customerId}")
    public String deleteCustomer(@PathVariable int customerId) {
        customerService.deleteCustomerById(customerId);
        return "redirect:/customer/all";
    }

    private boolean validateCreateRequest(Customer.Request request) {
        if (request.getName() == null || request.getName().isEmpty()) {
            return false;
        }

        String contactInfo = request.getContactInfo();
        if (contactInfo == null || contactInfo.isEmpty() || contactInfo.length() != 13) {
            return false;
        }
        return true;
    }
}
