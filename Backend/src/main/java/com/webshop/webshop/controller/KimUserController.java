package com.webshop.webshop.controller;

import com.webshop.webshop.repository.KimUserRepository;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KimUserController {
    private KimUserRepository userRepository;
    public KimUserController(KimUserRepository userRepository){this.userRepository = userRepository;}

}
