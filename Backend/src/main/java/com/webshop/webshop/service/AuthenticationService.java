package com.webshop.webshop.service;

import com.webshop.webshop.repository.KimUserRepository;
import org.jose4j.jwt.GeneralJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.security.auth.login.LoginException;

@Service
public class AuthenticationService {

    @Autowired
    TokenService tokenService;

    @Autowired
    KimUserRepository kimUserRepository;

    public String login(String userName, String password) throws LoginException, GeneralJwtException {
        var user = kimUserRepository.findByUserNameAndUserPassword(userName, password);
        if (user.isEmpty()) {
            throw new LoginException("User not found.");
        }
        return tokenService.generateToken(user.get());
    }
}
