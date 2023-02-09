package com.webshop.webshop.model;

import jakarta.persistence.*;

import java.util.List;

@Entity(name = "customer")
public class Customer extends Operator {
    @Column(name = "shoppingcart")
    private ShoppingCart userShoppingCart;
    @Column(name = "email")
    private String email;
    @Column(name = "order")
    private Order<List> userOrder;

    public Customer(Long id, String title, String firstname, String lastname, String street, int zip, String city, String country, Long id1, String email, String userPassword) {
        super(id, title, firstname, lastname, street, zip, city, country, id1);
        this.email = email;
    }

    public Customer() {
    }

    public ShoppingCart getUserShoppingCart() {
        return userShoppingCart;
    }

    public void setUserShoppingCart(ShoppingCart userShoppingCart) {
        this.userShoppingCart = userShoppingCart;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Order<List> getUserOrder() {
        return userOrder;
    }

    public void setUserOrder(Order<List> userOrder) {
        this.userOrder = userOrder;
    }
}
