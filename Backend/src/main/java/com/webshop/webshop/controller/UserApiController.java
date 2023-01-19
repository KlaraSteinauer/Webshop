package com.webshop.webshop.controller;

import com.webshop.webshop.repository.UserRepository;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserApiController {
    private UserRepository userRepository;
    public UserApiController(UserRepository userRepository){this.userRepository = userRepository;}

}
