package com.kgisl.project1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.kgisl.project1.Controllers.CustomerController;
import com.kgisl.project1.entity.Customer;
import com.kgisl.project1.service.CustomerService;

@ExtendWith(MockitoExtension.class)
public class CustomerControllerTest {

    @Mock
    private CustomerService customerService;

    @InjectMocks
    private CustomerController customerController;

    public static List<Customer> expected;
    public Customer customer1 = createCustomer(1, "mahesh", null);
    public Customer customer2 = createCustomer(2, "aravinth", "kumar");

    private static Customer createCustomer(int id, String firstName, String lastName) {
        Customer customer = new Customer();
        customer.setId(id);
        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        return customer;
    }

    @Test
    public void createCustomerTest() {
        when(customerService.createCustomer(customer1)).thenReturn(customer1);
        customerController.createCustomer(customer1);
        System.out.println("************createCustomerTest executed successfully!************");
    }

    @Test
    public void allCustomersTest() {
        expected = Arrays.asList(customer1, customer2);
        System.out.println("Expected Customers: " + expected);
        when(customerService.getAllCustomers()).thenReturn(expected);
        ResponseEntity<List<Customer>> actual = customerController.getAllCustomers();
        assertNotNull(actual);
        assertEquals(expected, actual.getBody());
        assertEquals(HttpStatus.OK, actual.getStatusCode());
        System.out.println("Actual Customers: " + actual.getBody());
        System.out.println("***********allCustomersTest executed successfully!************");
    }

    @Test
    public void getCustomerByIdTest() {
        int id = 1;
        when(customerService.getCustomerById(id)).thenReturn(customer1);
        ResponseEntity<Customer> actual = customerController.getCustomerById(id);
        assertNotNull(actual);
        assertEquals(customer1, actual.getBody());
        assertEquals(HttpStatus.OK, actual.getStatusCode());
        System.out.println("Actual Customer: " + actual.getBody());
        System.out.println("*************getCustomerByIdTest executed successfully!*************");
    }

    @Test
    public void updateCustomerTest() {
        Customer edit = createCustomer(1, "newFirstName", null);
        int id = 1;
        when(customerService.updateCustomer(id, edit)).thenReturn(edit);
        ResponseEntity<Customer> actual = customerController.updateCustomer(id,
                edit);
        assertNotNull(actual);
        assertEquals(edit, actual.getBody());
        System.out.println("Actual Customer after update: " + actual.getBody());
        System.out.println("**************updateCustomerTest executedsuccessfully!***************");
    }

    @Test
    public void deleteCustomerTest() {
        int id = 1;
        when(customerService.getCustomerById(id)).thenReturn(customer1);
        customerController.deleteCustomer(id);
        verify(customerService, times(1)).getCustomerById(id);
        System.out.println("**************deleteCustomerTest executed successfully!************");
    }
}
