package com.webshop.webshop.model;

import com.webshop.webshop.DTO.ShoppingCartDTO;
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
    @ManyToOne
    @JoinColumn(name = "kim_user_id", referencedColumnName = "id")
    private KimUser kimUser;
    @ManyToMany
    @JoinTable(name = "shopping_cart_products", joinColumns = @JoinColumn(name = "shopping_cart_id"),
    inverseJoinColumns = @JoinColumn(name = "product_id"))
    private List<Product> products;

    public ShoppingCartDTO convertToDto() {
        return new ShoppingCartDTO(
                this.getKimUser(),
                this.getProducts()
        );
    }
}
