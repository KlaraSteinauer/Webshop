package com.webshop.webshop.service;

import com.webshop.webshop.DTO.PositionDTO;
import com.webshop.webshop.model.KimUser;
import com.webshop.webshop.model.Position;
import com.webshop.webshop.model.Product;
import com.webshop.webshop.model.ShoppingCart;
import com.webshop.webshop.repository.PositionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PositionService {

    @Autowired
    private PositionRepository positionRepository;

    public Position createPosition(ShoppingCart cart, Product product) {
        Position position = new Position();
        position.setCart(cart);
        position.setProduct(product);
        position.setQuantity(1);
        cart.addPosition(position);
        return position;
    }

    public void removePosition(Position position) {
        positionRepository.delete(position);
    }

    public Position updateQuantity(Position position, int quantity) {
        position.setQuantity(quantity);
        return positionRepository.save(position);
    }
}
