package com.webshop.webshop.service;

import com.webshop.webshop.repository.PositionRepository;
import com.webshop.webshop.repository.ProductRepository;

public class PositionService {

    private PositionRepository positionRepository;

    public PositionService(PositionRepository positionRepository) {
        this.positionRepository = positionRepository;
    }

}
