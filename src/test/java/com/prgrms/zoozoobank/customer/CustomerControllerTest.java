package com.prgrms.zoozoobank.customer;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;

import java.util.Collections;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CustomerControllerTest {

    @Mock
    private CustomerService customerService;

    @Mock
    private Model model;

    @InjectMocks
    private CustomerController customerController;

    @Test
    public void testGetAllCustomers() {
        // Arrange
        when(customerService.getAllCustomers()).thenReturn(Collections.emptyList());

        // Act
        String viewName = customerController.getAllCustomers(model);

        // Assert
        verify(model).addAttribute("customers", Collections.emptyList());
        Assertions.assertThat(viewName).isEqualTo("customer-list");
    }

    @Test
    public void testCreateCustomer() {
        // Arrange
        String name = "John Doe";
        String contactInfo = "123-456-7890";

        // Act
        String redirect = customerController.createCustomer(name, contactInfo);

        // Assert
        verify(customerService).createCustomer(new Customer.Request(name, contactInfo));
        Assertions.assertThat(redirect).isEqualTo("redirect:/customer/all");
    }

    // Add more tests for other methods in CustomerController
}
