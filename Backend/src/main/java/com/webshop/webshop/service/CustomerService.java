package com.webshop.webshop.service;

import com.webshop.webshop.repository.CustomerRepository;

public class CustomerService {

    private CustomerRepository operatorRepository;

    public CustomerService(CustomerRepository operatorRepository) {
        this.operatorRepository = operatorRepository;
    }
}
