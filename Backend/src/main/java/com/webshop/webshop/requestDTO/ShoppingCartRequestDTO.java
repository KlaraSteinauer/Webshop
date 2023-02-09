package com.webshop.webshop.requestDTO;

import com.webshop.webshop.model.KimUser;
import com.webshop.webshop.model.Position;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public class ShoppingCartRequestDTO {
    @NotBlank
    private KimUser shoppingCartUser;
    @NotBlank
    private List<Position> shoppingCartPositions;

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
