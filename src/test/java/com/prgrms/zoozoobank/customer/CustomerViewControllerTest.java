package com.prgrms.zoozoobank.customer;

import com.prgrms.zoozoobank.customer.controller.CustomerViewController;
import com.prgrms.zoozoobank.customer.service.CustomerService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static org.mockito.ArgumentMatchers.eq;

import java.util.Collections;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CustomerViewControllerTest {

    @Mock
    private CustomerService customerService;

    @Mock
    private Model model;

    @Mock
    private RedirectAttributes redirectAttributes;

    @InjectMocks
    private CustomerViewController customerViewController;

    @Test
    @DisplayName("고객 리스트 조회시에 상호작용 확인")
    public void testGetAllCustomers() {
        // given
        when(customerService.getAllCustomers()).thenReturn(Collections.emptyList());

        // when
        String viewName = customerViewController.getAllCustomers(model);

        // then
        verify(model).addAttribute("customers", Collections.emptyList());
        Assertions.assertThat(viewName).isEqualTo("customer-list");
    }

    @Test
    @DisplayName("고객 생성시에 상호작용 확인")
    public void testCreateCustomer() {
        // given
        String name = "John Doe";
        String contactInfo = "123-456-7890";

        // when
        String redirect = customerViewController.createCustomer(name, contactInfo, redirectAttributes);

        // then
        Assertions.assertThat(redirect).isEqualTo("redirect:/customer/all");
    }

}
