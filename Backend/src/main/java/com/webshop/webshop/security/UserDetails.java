package com.webshop.webshop.security;

import com.webshop.webshop.enums.Role;


public class UserDetails {

    private final Long userId;
    private final String userName;
    private final Role userRole;



    public UserDetails(Long userId, String userName, Role userRole) {
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
}
