package com.webshop.webshop.service;

import com.webshop.webshop.DTO.ProductViewDTO;
import com.webshop.webshop.model.KimUser;
import com.webshop.webshop.model.Position;
import com.webshop.webshop.model.Product;
import com.webshop.webshop.model.ShoppingCart;
import com.webshop.webshop.repository.PositionRepository;
import com.webshop.webshop.repository.ShoppingCartRepository;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ShoppingCartService {

    @Autowired
    private KimUserService kimUserService;

    @Autowired
    private ProductService productService;

    @Autowired
    private PositionService positionService;

    @Autowired
    private PositionRepository positionRepository;

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    public ShoppingCartService(ShoppingCartRepository shoppingCartRepository) {
        this.shoppingCartRepository = shoppingCartRepository;
    }

    public Integer addProduct(Long userId, Long productId) {
        KimUser user = kimUserService.findById(userId);
        Product product = productService.findById(productId);
        ShoppingCart cart = user.getShoppingCart();
        Position position = null;
        if (cart == null) {
            cart = createCart(user);
        }
        Set<Position> positions = cart.getPositions();
        boolean isContained = false;
        if (positions != null) {
            for (Position pos : positions) {
                if (pos.getProduct().getId() == productId) {
                    positionService.updateQuantity(pos, pos.getQuantity() + 1);
                    isContained = true;
                }
            }
        }

        if (!isContained) {
            positionService.createPosition(cart, product);
        }
        shoppingCartRepository.save(cart);

        return cart.countItems();
    }

    public Integer removeProduct(Long userId, Long productId) {
        KimUser user = kimUserService.findById(userId);
        Product product = productService.findById(productId);
        ShoppingCart cart = user.getShoppingCart();
        if (cart == null) {
            throw new IllegalArgumentException("Cart does not contain Product with id: " + productId);
        }
        Set<Position> positions = cart.getPositions();
        Set<Position> positionsToDelete = new HashSet<>();
        boolean isContained = false;
        for (Position position : positions) {
            if (position.getProduct().getId() == productId) {
                cart.removePosition(position);
                positionsToDelete.add(position);
                isContained = true;
            }
        }
        if (!isContained) {
            throw new IllegalArgumentException("Cart does not contain Product with id: " + productId);
        }

        shoppingCartRepository.save(cart);
        positionRepository.deleteAll(positionsToDelete);

        return cart.countItems();
    }

    public Set<ProductViewDTO> findAllProductsInCart(Long userId) {
        KimUser user = kimUserService.findById(userId);
        ShoppingCart cart = user.getShoppingCart();
        Set<Position> positions = cart.getPositions();
        Set<ProductViewDTO> products = new HashSet<>();
        if (positions != null) {
            for (Position position : positions) {
                Integer quantity = position.getQuantity();
                ProductViewDTO product = position.getProduct().convertToViewDto();
                product.setQuantity(quantity);
                products.add(product);
            }
        }
        return products;
    }

    private ShoppingCart createCart(KimUser user) {
        ShoppingCart cart = new ShoppingCart();
        cart.setKimUser(user);
        user.setShoppingCart(cart);
        return cart;
    }

}
