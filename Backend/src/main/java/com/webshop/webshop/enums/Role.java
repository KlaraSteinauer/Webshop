package com.webshop.webshop.enums;

import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;

@ToString
public enum Role implements GrantedAuthority {
    ANONYMOUS,
    CUSTOMER,
    ADMIN;

    @Override
    public String getAuthority() {
        return this.name();
    }
}