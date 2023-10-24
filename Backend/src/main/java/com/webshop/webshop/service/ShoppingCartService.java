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

    /**
     * Adds a Product to a User's ShoppingCart.
     *
     * @param userId user id
     * @param productId product id
     * @return amount of Products in ShoppingCart
     */
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

    /**
     * Removes a Product from a User's ShoppingCart.
     *
     * @param userId user id
     * @param productId product id
     * @return amount of Products in ShoppingCart
     */
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
                positionsToDelete.add(position);
                isContained = true;
            }
        }
        if (!isContained) {
            throw new IllegalArgumentException("Cart does not contain Product with id: " + productId);
        } else {
            for (Position position : positionsToDelete) {
                cart.removePosition(position);
            }
        }
        shoppingCartRepository.save(cart);
        positionRepository.deleteAll(positionsToDelete);

        return cart.countItems();
    }

    /**
     * Fetches all Products in a User's ShoppingCart from Database.
     *
     * @param userId user id
     * @return Set of Products from ShoppingCart
     */
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

    /**
     * Creates a new ShoppingCart for a User.
     *
     * @param user user
     * @return ShoppingCart
     */
    private ShoppingCart createCart(KimUser user) {
        ShoppingCart cart = new ShoppingCart();
        cart.setKimUser(user);
        user.setShoppingCart(cart);
        return cart;
    }

}
