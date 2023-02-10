package com.webshop.webshop.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.util.List;

@Entity(name="shopping_cart")
public class ShoppingCart {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "shopping_cart_id")
    private Long shoppingCartId;

    @ManyToOne
    @JoinColumn(name="shopping_cart_user")
    private KimUser shoppingCartUser;


    @OneToMany(mappedBy = "shoppingCart") // mapping in #Position
    @JsonBackReference // JsonBackReference <-> JsonManagedReference counterparts in @OneToMany
    private List<Position> shoppingCartPositions;

/*WTF IS THIS?
    @ManyToMany
    @JoinTable(name="cartProduct",
    joinColumns=@JoinColumn(name = "cartsId", referencedColumnName = "shoppingCartID"),
    inverseJoinColumns = @JoinColumn(name = "productsID", referencedColumnName = "productID"))

     */

    public ShoppingCart(Long shoppingCartId, KimUser shoppingCartUser,
                        List<Position> shoppingCartPositions) {
        this.shoppingCartId = shoppingCartId;
        this.shoppingCartUser = shoppingCartUser;
        this.shoppingCartPositions = shoppingCartPositions;
    }

    public ShoppingCart() {
    }

    public Long getShoppingCartId() {
        return shoppingCartId;
    }

    public void setShoppingCartId(Long shoppingCartId) {
        this.shoppingCartId = shoppingCartId;
    }

    public KimUser getShoppingCartUser() {
        return shoppingCartUser;
    }

    public void setShoppingCartUser(KimUser shoppingCartUser) {
        this.shoppingCartUser = shoppingCartUser;
    }

    public List<Position> getShoppingCartPositions() {
        return shoppingCartPositions;
    }

    public void setShoppingCartPositions(List<Position> shoppingCartPositions) {
        this.shoppingCartPositions = shoppingCartPositions;
    }
}
