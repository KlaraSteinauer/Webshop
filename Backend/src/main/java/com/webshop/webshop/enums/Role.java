package com.webshop.webshop.enums;

public enum Role {
    ANONYMOUS(1),
    CUSTOMER(2),
    ADMIN(3);

    public final int value;

    private Role(int value) {
        this.value = value;
    }
}