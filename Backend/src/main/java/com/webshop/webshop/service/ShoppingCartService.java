package com.webshop.webshop.service;

import com.webshop.webshop.DTO.ShoppingCartDTO;
import com.webshop.webshop.model.Product;
import com.webshop.webshop.model.ShoppingCart;
import com.webshop.webshop.repository.ShoppingCartRepository;
import org.hibernate.ObjectNotFoundException;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ShoppingCartService {

    private ShoppingCartRepository shoppingCartRepository;

    public ShoppingCartService(ShoppingCartRepository shoppingCartRepository) {
        this.shoppingCartRepository = shoppingCartRepository;
    }

    public ShoppingCart addProduct(Product product, Long shoppingCartId) {
        var shoppingCartOptional = this.shoppingCartRepository.findById(shoppingCartId);
        if (shoppingCartOptional.isEmpty()) {
            throw new ObjectNotFoundException(shoppingCartOptional, "Shopping Cart not found.");
        } else {
            ShoppingCart shoppingCart = shoppingCartOptional.get();
            shoppingCart.getProducts().add(product);
            return shoppingCartRepository.save(shoppingCart);
        }
    }
}
