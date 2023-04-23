package com.webshop.webshop.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "shopping_cart")
public class ShoppingCart {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long shoppingCartId;
    @OneToOne
    @JoinColumn(name = "kim_user_id", referencedColumnName = "id")
    private KimUser kimUser;
    @ManyToMany
    private List<Product> products;
}
