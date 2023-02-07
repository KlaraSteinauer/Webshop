package com.webshop.webshop.model;

import jakarta.persistence.*;

import java.util.List;

@Entity(name="shoppingCart")
public class ShoppingCart {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "shoppingCartID")
    private Long shoppingCartID;
    @ManyToMany
    @JoinTable(name="cartProduct",
    joinColumns=@JoinColumn(name = "cartsId", referencedColumnName = "shoppingCartID"),
    inverseJoinColumns = @JoinColumn(name = "productsID", referencedColumnName = "productID"))
    private List<Product> shoppingCartProducts;

    public List<Product> getShoppingCartProducts() {
        return shoppingCartProducts;
    }

    public void setShoppingCartProducts(List<Product> shoppingCartProducts) {
        this.shoppingCartProducts = shoppingCartProducts;
    }

    public ShoppingCart(Long shoppingCartID, List<Product> shoppingCartProducts) {
        this.shoppingCartID = shoppingCartID;
        this.shoppingCartProducts = shoppingCartProducts;
    }

    public ShoppingCart() {
    }

    public Long getShoppingCartID() {
        return shoppingCartID;
    }

    public void setShoppingCartID(Long shoppingCartID) {
        this.shoppingCartID = shoppingCartID;
    }

}
