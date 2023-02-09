package com.webshop.webshop.model;

import jakarta.persistence.*;

@Entity (name = "order")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "order_id")
    private Long orderId;
    @Column(name = "client_id")
    private KimUser client;
    @OneToOne
    @JoinTable(name = "order_shopping_cart",
    joinColumns = @JoinColumn(name ="order_id", referencedColumnName = "order_id"),
    inverseJoinColumns = @JoinColumn(name = "shopping_cart_id", referencedColumnName = "shoppingcartID"))
    @Column(name = "shopping_cart")
    private ShoppingCart shoppingCart;
    @Column(name = "order_status")
    private String orderStatus;



    public Order() {
    }


}
