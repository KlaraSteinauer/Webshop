package com.webshop.webshop.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

@Entity(name = "position")
public class Position {

    @Id
    @GeneratedValue
    @Column(name = "position_id")
    private Long positionId;

    @ManyToOne
    @JoinColumn(name = "shopping_cart_id", nullable = false)
    @JsonManagedReference
    private ShoppingCart shoppingCart;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    public Position(Long positionId, ShoppingCart shoppingCart, Product product, Integer quantity) {
        this.positionId = positionId;
        this.shoppingCart = shoppingCart;
        this.product = product;
        this.quantity = quantity;
    }

    public Position() {
    }

    public Long getPositionId() {
        return positionId;
    }

    public void setPositionId(Long positionId) {
        this.positionId = positionId;
    }

    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }

    public void setShoppingCart(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}