package com.webshop.webshop.controller;

import com.webshop.webshop.DTO.LoginDTO;
import com.webshop.webshop.service.AuthenticationService;
import com.webshop.webshop.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.jose4j.jwt.GeneralJwtException;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.LoginException;

@RestController
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    private final TokenService tokenService;


    @PostMapping("/login")
    public String login(@RequestBody LoginDTO loginDTO) throws GeneralJwtException, LoginException {
        String token = authenticationService.login(loginDTO.getUsername(), loginDTO.getPassword());
        String pre = "Bearer ";
        if (!token.substring(0, 5).equals(pre)) {
            token = pre.concat(token);
        }
        return token;
    }

    @GetMapping("/isAdmin")
    public boolean isAdmin(@RequestHeader(name = "Authorization") String token) {
        return tokenService.isAdmin(token);
    }

}
