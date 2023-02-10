package com.webshop.webshop.controller;

import com.webshop.webshop.service.PositionService;

public class PositionController {
    private PositionService positionService;

    private PositionController(PositionService positionService) {
        this.positionService = positionService;
    }
}
