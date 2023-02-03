package com.webshop.webshop.controller;

import com.webshop.webshop.repository.CustomerRepository;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerController {
    private CustomerRepository userRepository;
    public CustomerController(CustomerRepository userRepository){this.userRepository = userRepository;}

}
