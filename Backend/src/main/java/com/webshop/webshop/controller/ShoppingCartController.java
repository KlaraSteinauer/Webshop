package com.webshop.webshop.controller;

import com.webshop.webshop.DTO.ShoppingCartDTO;
import com.webshop.webshop.model.Product;
import com.webshop.webshop.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/shoppingCart")
public class ShoppingCartController {
    @Autowired
    private ShoppingCartService shoppingCartService;

    private ShoppingCartController(ShoppingCartService shoppingCartService) {
        this.shoppingCartService = shoppingCartService;
    }

    @PostMapping("/addProduct/{ShoppingCartId}")
    public ResponseEntity<ShoppingCartDTO> addProductToCart(@RequestBody Product product, @PathVariable Long ShoppingCartId) {
        ShoppingCartDTO shoppingCartDTO = shoppingCartService.addProduct(product, ShoppingCartId);
        if (shoppingCartDTO == null) {
            new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(shoppingCartDTO, HttpStatus.OK);
    }

}
