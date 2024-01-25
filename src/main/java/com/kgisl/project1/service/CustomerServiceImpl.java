package com.kgisl.project1.service;

import com.kgisl.project1.entity.Customer;
import com.kgisl.project1.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Customer createCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public Customer getCustomerById(long id) {
        return customerRepository.findById((int) id).orElse(null);
    }

    @Override
    public Customer updateCustomer(int id, Customer updatedCustomer) {
        Customer customer = customerRepository.findById(id).orElse(null);
        if (customer != null) {
            customer.setFirstName(updatedCustomer.getFirstName());
            customer.setLastName(updatedCustomer.getLastName());
            customer.setEmail(updatedCustomer.getEmail());
            return customerRepository.save(customer);
        }
        return null;
    }

    @Override
    public void deleteCustomer(int id) {
        customerRepository.deleteById(id);
    }

}