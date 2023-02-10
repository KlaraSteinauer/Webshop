package com.webshop.webshop.requestDTO;

import com.webshop.webshop.model.Product;
import com.webshop.webshop.model.ShoppingCart;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public class PositionRequestDTO {


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
