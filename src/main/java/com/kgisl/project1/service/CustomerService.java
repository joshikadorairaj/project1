package com.kgisl.project1.service;

import com.kgisl.project1.entity.Customer;

import java.util.List;

public interface CustomerService {

    List<Customer> getAllCustomers();

    Customer createCustomer(Customer customer);

    Customer getCustomerById(long l);

    Customer updateCustomer(int id, Customer updatedCustomer);

    void deleteCustomer(int id);
}