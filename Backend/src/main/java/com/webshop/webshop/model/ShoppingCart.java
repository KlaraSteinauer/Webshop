package com.webshop.webshop.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@Entity
public class ShoppingCart {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "shoppingcartID")
    private Long shoppingcartID;
    @Column(name = "shoppingcart")
    private List<Product> shoppingcartProducts;

    public List<Product> getShoppingcartProducts() {
        return shoppingcartProducts;
    }

    public void setShoppingcartProducts(List<Product> shoppingcartProducts) {
        this.shoppingcartProducts = shoppingcartProducts;
    }

    public ShoppingCart(Long shoppingcartID, List<Product> shoppingcartProducts) {
        this.shoppingcartID = shoppingcartID;
        this.shoppingcartProducts = shoppingcartProducts;
    }

    public ShoppingCart() {
    }

    public Long getShoppingcartID() {
        return shoppingcartID;
    }

    public void setShoppingcartID(Long shoppingcartID) {
        this.shoppingcartID = shoppingcartID;
    }

}
