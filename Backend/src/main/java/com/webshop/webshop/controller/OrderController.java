package com.webshop.webshop.controller;

import com.webshop.webshop.service.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private PositionService positionService;

    private OrderController(PositionService positionService) {
        this.positionService = positionService;
    }
}
