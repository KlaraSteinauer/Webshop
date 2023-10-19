package com.webshop.webshop.controller;

import com.webshop.webshop.DTO.ProductViewDTO;
import com.webshop.webshop.DTO.ShoppingCartDTO;
import com.webshop.webshop.model.Product;
import com.webshop.webshop.security.KimUserDetails;
import com.webshop.webshop.service.ShoppingCartService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/carts")
@RequiredArgsConstructor
public class ShoppingCartController {

    @Autowired
    private final ShoppingCartService shoppingCartService;

    @PostMapping("/{productId}")
    public ResponseEntity<Integer> addProduct(@AuthenticationPrincipal Optional<KimUserDetails> user, @PathVariable Long productId) {
        Long userId = user.get().getUserId();
        try {
            Integer productsInCart = shoppingCartService.addProduct(userId, productId);
            return new ResponseEntity<>(productsInCart, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Integer> removeProduct(@AuthenticationPrincipal Optional<KimUserDetails> user, @PathVariable Long productId) {
        Long userId = user.get().getUserId();
        try {
            Integer productsInCart = shoppingCartService.removeProduct(userId, productId);
            return new ResponseEntity<>(productsInCart, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<Set<ProductViewDTO>> findAllProductsInShoppingCart(@AuthenticationPrincipal Optional<KimUserDetails> user) {
        Long userId = user.get().getUserId();
        try {
            Set<ProductViewDTO> response = shoppingCartService.findAllProductsInCart(userId);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(Collections.emptySet(), HttpStatus.NOT_FOUND);
        }
    }



}
