package com.webshop.webshop.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "order")
public class Order {

    @Id
    @GeneratedValue
    @Column(name = "order_id")
    private Long orderId;

    @ManyToOne
    @JoinColumn(name = "shopping_cart_id", nullable = false)
    private ShoppingCart shoppingCart;

    @Column(name = "price")
    private Double price;
}