package com.webshop.webshop.service;

import com.webshop.webshop.enums.Role;
import com.webshop.webshop.model.KimUser;
import com.webshop.webshop.security.KimUserDetails;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.security.auth.login.LoginException;
import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

import static io.jsonwebtoken.SignatureAlgorithm.HS256;

@Service
public class TokenService {

    @Autowired
    private KimUserService kimUserService;

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
     * Generates a token for a User.
     *
     * @param kimUser KimUser
     * @return JWT token
     */
    public String generateToken(KimUser kimUser) throws GeneralJwtException {

        if (kimUser.getId() == null
                || (kimUser.getUserName() == null
                || kimUser.getUserName().isEmpty())
                || kimUser.getRole() == null) {
            throw new IllegalArgumentException("User:  "
                    + kimUser.getId() + "/"
                    + kimUser.getUserName() + "/"
                    + kimUser.getUserPassword()
                    + " invalid!");
        }
        boolean isActive = checkUserActivity(kimUser.getId());
        if (!isActive) {
            throw new GeneralJwtException("User with id: " + kimUser.getId() + " is inactive!");
        }
        String result = null;
        Date expirationDate = new Date(System.currentTimeMillis() + validity * 60000);
        try {
            JwtClaims claims = new JwtClaims();
            result = Jwts.builder()
                    .claim("id", kimUser.getId())
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

    /**
     * Generates a token for a User from UserDetails.
     *
     * @param kimUserDetails UserDetails holding User information
     * @return JWT token
     */
    public String generateTokenFromUserDetails(KimUserDetails kimUserDetails) throws GeneralJwtException {
        if (kimUserDetails.getUserId() == null
                || (kimUserDetails.getUserName() == null
                || kimUserDetails.getUserName().isEmpty())
                || kimUserDetails.getUserRole() == null) {
            throw new IllegalArgumentException("User:  "
                    + kimUserDetails.getUserId() + "/"
                    + kimUserDetails.getUserName() + "/"
                    + kimUserDetails.getPassword()
                    + " invalid!");
        }
        boolean isActive = checkUserActivity(kimUserDetails.getUserId());
        if (!isActive) {
            throw new GeneralJwtException("User with id: " + kimUserDetails.getUserId() + " is inactive!");
        }
        String result = null;
        Date expirationDate = new Date(System.currentTimeMillis() + validity * 60000);
        try {
            JwtClaims claims = new JwtClaims();
            result = Jwts.builder()
                    .claim("id", kimUserDetails.getUserId())
                    .claim("sub", kimUserDetails.getUserName())
                    .claim("role", kimUserDetails.getUserRole())
                    .setExpiration(expirationDate)
                    .signWith(HS256, key)
                    .compact();
        } catch (Exception e) {
            throw new GeneralJwtException("Invalid token.");
        }
        return result;
    }

    /**
     * Parses a token, creating UserDetails from it's claims.
     *
     * @param jwt JWT token
     * @return UserDetails
     * @throws GeneralJwtException
     */
    public Optional<KimUserDetails> parseToken(String jwt) throws GeneralJwtException {

        Jws<Claims> jwsClaims;
        jwsClaims = Jwts.parser()
                .setSigningKey(key.getEncoded())
                .parseClaimsJws(jwt);

        Long userId = jwsClaims.getBody().get("id", Long.class);
        String sub = jwsClaims.getBody().getSubject();
        String userRoleString = jwsClaims.getBody().get("role").toString();
        Role userRole;
        try {
            userRole = Role.valueOf(userRoleString);
        } catch (RequiredTypeException e) {
            throw new GeneralJwtException("Token includes invalid role!");
        }
        return Optional.of(new KimUserDetails(userId, sub, userRole));
    }

    /**
     * Creates a Map from JWT token claims.
     *
     * @param token JWT token
     * @return claims Map
     * @throws InvalidJwtException
     */
    public Map<String, Object> getClaimsFromToken(String token) throws InvalidJwtException {
        JwtConsumer jwtc = new JwtConsumerBuilder()
                .setRequireExpirationTime()
                .setVerificationKey(key)
                .build();
        JwtClaims claims = jwtc.processToClaims(token);
        return claims.getClaimsMap();
    }

    /**
     * Checks whether a User is active.
     *
     * @param userId user id
     * @return true if User is active
     *          / false otherwise
     */
    private boolean checkUserActivity(Long userId) {
        KimUser user = kimUserService.findById(userId);
        return user.isActive();
    }

    /**
     * Determines whether a JWT token belongs to a User with the Role ADMIN.
     *
     * @param token JWT token
     * @return true if User has the Role ADMIN
     *          / false otherwise
     */
    public boolean isAdmin(String token) {
        Optional<KimUserDetails> var;
        String trimmedToken = token.replaceAll("Bearer ", "");
        try {
            var = parseToken(trimmedToken);
        } catch (GeneralJwtException e) {
            throw new RuntimeException(e);
        }
        KimUserDetails kimUserDetails = var.get();
        return kimUserDetails.getUserRole() == Role.ADMIN;
    }

}
