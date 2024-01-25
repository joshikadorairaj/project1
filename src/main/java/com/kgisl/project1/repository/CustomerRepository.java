package com.kgisl.project1.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kgisl.project1.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    
}