package com.webshop.webshop.service;

import com.webshop.webshop.DTO.ShoppingCartDTO;
import com.webshop.webshop.model.Product;
import com.webshop.webshop.model.ShoppingCart;
import com.webshop.webshop.repository.ShoppingCartRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ShoppingCartService {

    private ShoppingCartRepository shoppingCartRepository;

    public ShoppingCartService(ShoppingCartRepository shoppingCartRepository) {
        this.shoppingCartRepository = shoppingCartRepository;
    }

    public ShoppingCartDTO addProduct(Product product, Long shoppingCartId) {
        Optional<ShoppingCart> shoppingCartOptional = this.shoppingCartRepository.findById(shoppingCartId);
        if (shoppingCartOptional.isPresent()) {
            ShoppingCart shoppingCart = shoppingCartOptional.get();
            shoppingCart.getProducts().add(product);
            return toDto(shoppingCartRepository.save(shoppingCart));
        }
        return null;
    }

    private ShoppingCartDTO toDto(ShoppingCart shoppingCart) {
        return new ShoppingCartDTO(shoppingCart.getKimUser(), shoppingCart.getProducts());
    }

}
