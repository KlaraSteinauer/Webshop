package com.webshop.webshop.service;

import com.webshop.webshop.model.KimUser;
import com.webshop.webshop.repository.KimUserRepository;
import com.webshop.webshop.security.AuthenticationFilter;
import com.webshop.webshop.security.KimUserDetails;
import org.jose4j.jwt.GeneralJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.security.auth.login.LoginException;
import java.util.Optional;

@Service
public class AuthenticationService {

    @Autowired
    TokenService tokenService;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    KimUserRepository kimUserRepository;

    public String login(String userName, String userPassword) throws LoginException, GeneralJwtException {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName, userPassword));
            return tokenService.generateTokenFromUserDetails((KimUserDetails) authentication.getPrincipal());
        }
        catch (Exception e) {
            throw new LoginException(e.getMessage());
        }
    }
}
