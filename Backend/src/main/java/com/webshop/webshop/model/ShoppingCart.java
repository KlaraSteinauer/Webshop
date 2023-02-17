package com.webshop.webshop.model;

import jakarta.persistence.*;

import java.util.List;

@Entity(name = "shopping_cart")
public class ShoppingCart {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "shopping_cart_id")
    private Long shoppingCartId;

    @OneToOne(mappedBy = "userShoppingCart")
    private KimUser kimUser;
    @ManyToMany
    private List<Product> products;

    public ShoppingCart() {
    }

    public ShoppingCart(KimUser kimUser, List<Product> products) {
        this.kimUser = kimUser;
        this.products = products;
    }

    public Long getShoppingCartId() {
        return shoppingCartId;
    }

    public void setShoppingCartId(Long shoppingCart) {
        this.shoppingCartId = shoppingCart;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> shoppingCartProducts) {
        this.products = shoppingCartProducts;
    }

    public KimUser getKimUser() {
        return kimUser;
    }

    public void setKimUser(KimUser kimUser) {
        this.kimUser = kimUser;
    }
}
