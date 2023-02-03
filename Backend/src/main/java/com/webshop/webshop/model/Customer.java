package com.webshop.webshop.model;

import jakarta.persistence.*;

import java.util.List;

@Entity(name = "customer")
public class Customer extends Operator {
    @Column(name = "lastname")
    private String lastName;
    @Column(name = "firstname")
    private String firstName;
    @Column(name = "password")
    private String userPassword;
    @Column(name = "shoppingcart")
    private ShoppingCart shoppingCart;
    @Column(name = "order")
    private Order<List> userOrder;


    public Customer(Long id, String userName, String lastName, String firstName, String userPassword, ShoppingCart shoppingCart, Order<List> userOrder) {
        super(id, userName);
        this.lastName = lastName;
        this.firstName = firstName;
        this.userPassword = userPassword;
        this.shoppingCart = shoppingCart;
        this.userOrder = userOrder;
    }

    public Customer() {
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }

    public void setShoppingCart(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    public Order<List> getUserOrder() {
        return userOrder;
    }

    public void setUserOrder(Order<List> userOrder) {
        this.userOrder = userOrder;
    }
}
