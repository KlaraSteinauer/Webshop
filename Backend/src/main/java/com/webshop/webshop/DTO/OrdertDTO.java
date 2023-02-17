package com.webshop.webshop.DTO;

import com.webshop.webshop.model.Product;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public class OrdertDTO {


    // private ShoppingCart shoppingCart;

    @NotNull
    @PositiveOrZero
    private Product product;

    @NotNull
    @PositiveOrZero
    private Integer quantity;

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
