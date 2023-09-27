package com.webshop.webshop.DTO;

import com.webshop.webshop.model.KimUser;
import com.webshop.webshop.model.Product;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public class ShoppingCartDTO {


    private KimUser shoppingCartUser;
    private List<Product> shoppingCartProducts;

    public ShoppingCartDTO(KimUser shoppingCartUser, List<Product> shoppingCartProducts) {
        this.shoppingCartUser = shoppingCartUser;
        this.shoppingCartProducts = shoppingCartProducts;
    }

    public KimUser getShoppingCartUser() {
        return shoppingCartUser;
    }

    public void setShoppingCartUser(KimUser shoppingCartUser) {
        this.shoppingCartUser = shoppingCartUser;
    }

    public List<Product> getShoppingCartProducts() {
        return shoppingCartProducts;
    }

    public void setShoppingCartProducts(List<Product> shoppingCartProducts) {
        this.shoppingCartProducts = shoppingCartProducts;
    }
}
