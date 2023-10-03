package com.webshop.webshop.service;

import com.webshop.webshop.model.KimUser;
import com.webshop.webshop.repository.KimUserRepository;
import org.jose4j.jwt.GeneralJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.security.auth.login.LoginException;
import java.util.Optional;

@Service
public class AuthenticationService {

    @Autowired
    TokenService tokenService;

    @Autowired
    KimUserRepository kimUserRepository;

    public String login(String userName, String userPassword) throws LoginException, GeneralJwtException {
        Optional<KimUser> user = kimUserRepository.findByUserNameAndUserPassword(userName, userPassword);
        if (user.isEmpty()) {
            throw new LoginException("Login failed!");
        }
        return tokenService.generateToken(user.get());
    }
}
