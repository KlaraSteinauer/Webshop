package com.webshop.webshop.security;

import com.webshop.webshop.enums.Role;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;


@ToString
public class KimUserDetails implements UserDetails {

    private final Long userId;
    private final String userName;

    private String userPassword;
    private final Role userRole;



    public KimUserDetails(Long userId, String userName, String userPassword, Role userRole) {
        this.userId = userId;
        this.userName = userName;
        this.userPassword = userPassword;
        this.userRole = userRole;
    }

    public KimUserDetails(Long userId, String userName, Role userRole) {
        this.userId = userId;
        this.userName = userName;
        this.userRole = userRole;
    }


    public Long getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public Role getUserRole() {
        return userRole;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(this.userRole);
    }

    @Override
    public String getPassword() {
        return this.userPassword;
    }

    @Override
    public String getUsername() {
        return this.userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
