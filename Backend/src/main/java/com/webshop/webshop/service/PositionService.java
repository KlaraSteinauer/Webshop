package com.webshop.webshop.service;

import com.webshop.webshop.repository.OrderRepository;
import org.springframework.stereotype.Service;

@Service
public class PositionService {

    private OrderRepository positionRepository;

    public PositionService(OrderRepository positionRepository) {
        this.positionRepository = positionRepository;
    }

}
