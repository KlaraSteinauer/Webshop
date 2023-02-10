package com.webshop.webshop.service;

import com.webshop.webshop.repository.ShoppingCartRepository;

public class ShoppingCartService {

    private ShoppingCartRepository shoppingCartRepository;

    public ShoppingCartService(ShoppingCartRepository shoppingCartRepository) {
        this.shoppingCartRepository = shoppingCartRepository;
    }

}
