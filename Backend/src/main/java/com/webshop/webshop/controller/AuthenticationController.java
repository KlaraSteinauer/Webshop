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


    /**
     * User login, creates a JWT token for a User.
     *
     * @param loginDTO userName, userPassword
     * @return JWT token
     * @throws GeneralJwtException
     * @throws LoginException
     */
    @PostMapping("/login")
    public String login(@RequestBody LoginDTO loginDTO) throws GeneralJwtException, LoginException {
        String token = authenticationService.login(loginDTO.getUsername(), loginDTO.getPassword());
        String pre = "Bearer ";
        if (!token.substring(0, 5).equals(pre)) {
            token = pre.concat(token);
        }
        return token;
    }

    /**
     * Determines whether a JWT token belongs to a User with the Role ADMIN.
     *
     * @param token JWT token
     * @return true if User has the Role ADMIN
     *          / false otherwise
     */
    @GetMapping("/isAdmin")
    public boolean isAdmin(@RequestHeader(name = "Authorization") String token) {
        return tokenService.isAdmin(token);
    }

}
