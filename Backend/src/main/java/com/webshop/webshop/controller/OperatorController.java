package com.webshop.webshop.controller;

import com.webshop.webshop.repository.OperatorRepository;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OperatorController {
    private OperatorRepository userRepository;
    public OperatorController(OperatorRepository userRepository){this.userRepository = userRepository;}

}
