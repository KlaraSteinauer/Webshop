package com.webshop.webshop.service;

import com.webshop.webshop.enums.Role;
import com.webshop.webshop.config.model.KimUser;
import com.webshop.webshop.security.UserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.RequiredTypeException;
import jakarta.annotation.PostConstruct;
import org.jose4j.jwt.GeneralJwtException;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;
import org.jose4j.keys.HmacKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

import static io.jsonwebtoken.SignatureAlgorithm.HS256;

@Service
public class TokenService {

    @Value("${application.jwt.secret}")
    private String secret;

    @Value("${application.jwt.validity-in-minutes}")
    private Integer validity;

    private Key key = null;

    @PostConstruct
    public void initKey() {
        key = new HmacKey(secret.getBytes());
    }


    /**
     * Generates a Token out of the given User details.
     *
     * @param kimUser kimUser to make the token out of
     * @return String The token
     */
    public String generateToken(KimUser kimUser) throws GeneralJwtException {
        if (kimUser.getUserId() == null
                || (kimUser.getUserName() == null
                || kimUser.getUserName().isEmpty())
                || kimUser.getRole() == null) {
            throw new IllegalArgumentException("User:  "
                    + kimUser.getUserId() + "/"
                    + kimUser.getUserName() + "/"
                    + kimUser.getUserPassword()
                    + " invalid!");
        }
        String result = null;
        Date expirationDate = new Date(System.currentTimeMillis() + validity * 60000);
        try {
            JwtClaims claims = new JwtClaims();
            result = Jwts.builder()
                    .claim("id", kimUser.getUserId())
                    .claim("sub", kimUser.getUserName())
                    .claim("role", kimUser.getRole())
                    .setExpiration(expirationDate)
                    .signWith(HS256, key)
                    .compact();
        } catch (Exception e) {
            throw new GeneralJwtException("Invalid token.");
        }
        return result;
    }

    public Optional<UserDetails> parseToken(String jwt) throws GeneralJwtException {
        Jws<Claims> jwsClaims;
        jwsClaims = Jwts.parser()
                .setSigningKey(key.getEncoded())
                .parseClaimsJws(jwt);

        Long userId = jwsClaims.getBody().get("id", Long.class);
        String sub = jwsClaims.getBody().getSubject();
        String userRoleString = jwsClaims.getBody().get("role", String.class);
        Role userRole;
        try {
            userRole = Role.valueOf(userRoleString);
        } catch (RequiredTypeException e) {
            throw new GeneralJwtException("Token includes invalid role!");
        }
        return Optional.of(new UserDetails(userId, sub, userRole));
    }




    public Boolean validateToken(String token) {
    try {
    getClaimsFromToken(token);
    } catch (InvalidJwtException e) {
    return false;
    }
    return true;
    }


    public Map<String, Object> getClaimsFromToken(String token) throws InvalidJwtException {
    JwtConsumer jwtc = new JwtConsumerBuilder()
        .setRequireExpirationTime()
        .setVerificationKey(key)
        .build();
    JwtClaims claims = jwtc.processToClaims(token);
    return claims.getClaimsMap();
    }


    public boolean isAdmin(String token) {
        Optional<UserDetails> var;
        try {
            var = parseToken(token);
        } catch (GeneralJwtException e) {
            throw new RuntimeException(e);
        }
        UserDetails userDetails = var.get();
        return userDetails.getUserRole() == Role.ADMIN;
    }


}
