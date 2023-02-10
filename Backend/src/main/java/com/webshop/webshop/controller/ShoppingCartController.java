package com.webshop.webshop.controller;

import com.webshop.webshop.service.ShoppingCartService;

public class ShoppingCartController {
    private ShoppingCartService shoppingCartService;

    private ShoppingCartController(ShoppingCartService shoppingCartService) {
        this.shoppingCartService = shoppingCartService;
    }
}
