package com.webshop.webshop.security;

import com.webshop.webshop.enums.Role;
import com.webshop.webshop.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jose4j.jwt.GeneralJwtException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AuthenticationFilter extends OncePerRequestFilter {

private final TokenService tokenService;

    public AuthenticationFilter(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // Get authorization header
        String bearer = request.getHeader("Authorization");

        if (bearer == null || !bearer.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Create authorization token
        Optional<UsernamePasswordAuthenticationToken> authToken = null;
        try {
            authToken = createAuthToken(bearer.split(" ")[1]);
        } catch (GeneralJwtException e) {
            throw new RuntimeException(e);
        }

        // JWT is invalid, auth token could not be created
        if (authToken.isEmpty()) {
            filterChain.doFilter(request, response);
            return;
        }

        // JWT is valid, set auth token
        SecurityContextHolder.getContext().setAuthentication(authToken.get());
        filterChain.doFilter(request, response);
    }

    private Optional<UsernamePasswordAuthenticationToken> createAuthToken(String jwt) throws GeneralJwtException {
        // Parse JWT
        Optional<UserDetails> userDetails = tokenService.parseToken(jwt);

        // JWT is invalid
        if (userDetails.isEmpty()) {
            return Optional.empty();
        }

        List<GrantedAuthority> authorities = new ArrayList<>();

        // Add admin role if user is admin
        if (userDetails.get().getUserRole().equals(Role.ADMIN)) {
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }

        // Create auth token
        return Optional.of(new UsernamePasswordAuthenticationToken(userDetails, null, authorities));
    }
}
