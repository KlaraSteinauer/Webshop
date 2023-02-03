package com.webshop.webshop.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;

import java.util.List;
@Entity(name = "admin")
public class Admin extends Customer {
    @Column(name = "admin")
    @NotBlank
    private boolean isAdmin;

    public Admin(Long id, String userName, String lastName, String firstName, String userPassword, ShoppingCart shoppingCart, Order<List> userOrder, boolean isAdmin) {
        super(id, userName, lastName, firstName, userPassword, shoppingCart, userOrder);
        this.isAdmin = isAdmin;
    }

    public Admin() {
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }
}
