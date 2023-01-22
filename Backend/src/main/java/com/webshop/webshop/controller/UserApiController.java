package com.webshop.webshop.controller;

import com.webshop.webshop.repository.KimUserRepository;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserApiController {
    private KimUserRepository userRepository;
    public UserApiController(KimUserRepository userRepository){this.userRepository = userRepository;}

}
