package com.webshop.webshop.controller;

import com.webshop.webshop.DTO.ShoppingCartDTO;
import com.webshop.webshop.model.Product;
import com.webshop.webshop.service.ShoppingCartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/shoppingCart")
@RequiredArgsConstructor
public class ShoppingCartController {

    private final ShoppingCartService shoppingCartService;

    @PostMapping("/addProduct/{ShoppingCartId}")
    public ResponseEntity<ShoppingCartDTO> addProductToCart(@RequestBody Product product, @PathVariable Long ShoppingCartId) {
        ShoppingCartDTO shoppingCartDTO = shoppingCartService.addProduct(product, ShoppingCartId).convertToDto();
        if (shoppingCartDTO == null) {
            new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(shoppingCartDTO, HttpStatus.OK);
    }
}
