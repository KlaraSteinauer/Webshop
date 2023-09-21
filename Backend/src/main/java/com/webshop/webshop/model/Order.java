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
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "order_id")
    private Long orderId;

    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "order_id", referencedColumnName = "orderId")
    private ShoppingCart shoppingCart;

    @Column(name = "price")
    private Double price;
}