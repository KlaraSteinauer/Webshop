package com.webshop.webshop.controller;

import com.webshop.webshop.DTO.LoginDTO;
import com.webshop.webshop.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.jose4j.jwt.GeneralJwtException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.security.auth.login.LoginException;

@RestController
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public String login(@RequestBody LoginDTO loginDTO) throws GeneralJwtException, LoginException {
        return "Bearer " + authenticationService.login(loginDTO.getUsername(), loginDTO.getPassword());
    }

}
