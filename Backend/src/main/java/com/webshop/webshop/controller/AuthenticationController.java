package com.webshop.webshop.controller;

import com.webshop.webshop.DTO.LoginDTO;
import com.webshop.webshop.service.AuthenticationService;
import com.webshop.webshop.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.jose4j.jwt.GeneralJwtException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.security.auth.login.LoginException;

@RestController
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    private final TokenService tokenService;


    @PostMapping("/login")
    public String login(@RequestBody LoginDTO loginDTO) throws GeneralJwtException, LoginException {
        return authenticationService.login(loginDTO.getUsername(), loginDTO.getPassword());
    }

    @GetMapping("/isAdmin")
    public boolean isAdmin(String token) {
        return tokenService.isAdmin(token);
    }

}
