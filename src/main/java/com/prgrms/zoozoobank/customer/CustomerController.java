package com.prgrms.zoozoobank.customer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

import static com.prgrms.zoozoobank.customer.CustomerValidator.validateCreateRequest;

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
        Customer.Response response = customerService.getCustomerById(customerId);
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
                                 @RequestParam(value = "contactInfo") String contactInfo,
                                 Model model, RedirectAttributes redirectAttributes) {
        Customer.Request request = new Customer.Request(name, contactInfo);
        Customer.Response response = validateCreateRequest(request);
        if (response != null) {
            // 검증 실패 메시지를 RedirectAttributes에 추가
            redirectAttributes.addFlashAttribute("errorMessage", response.getReturnMessage());
            return "redirect:/customer/create"; // 실패 시 다시 폼 페이지로 리다이렉션
        }

        customerService.createCustomer(request);
        return "redirect:/customer/all";
    }


    @PostMapping("/delete/{customerId}")
    public String deleteCustomer(@PathVariable int customerId) {
        customerService.deleteCustomerById(customerId);
        return "redirect:/customer/all";
    }

}
