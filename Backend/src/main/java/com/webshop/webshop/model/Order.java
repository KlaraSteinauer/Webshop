package com.webshop.webshop.model;

import jakarta.persistence.*;

@Entity (name = "order")
public class Order<T> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "order_id")
    private Long orderID;
    @OneToOne
    @JoinColumn(name = "client_id")
    private Customer client;
    @OneToOne
    @JoinTable(name = "order_shopping_cart",
    joinColumns = @JoinColumn(name ="order_id", referencedColumnName = "order_id"),
    inverseJoinColumns = @JoinColumn(name = "shopping_cart_id", referencedColumnName = "shoppingcartID"))
    private ShoppingCart shoppingCart;
    @Column(name = "order_status")
    private String orderStatus;

    public Order(Long orderID, Customer client, ShoppingCart shoppingCart, String orderStatus) {
        this.orderID = orderID;
        this.client = client;
        this.shoppingCart = shoppingCart;
        this.orderStatus = orderStatus;
    }

    public Order() {
    }

    public Long getOrderID() {
        return orderID;
    }

    public void setOrderID(Long orderID) {
        this.orderID = orderID;
    }

    public Customer getClient() {
        return client;
    }

    public void setClient(Customer client) {
        this.client = client;
    }

    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }

    public void setShoppingCart(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }
}
