package com.webshop.webshop.controller;

import com.webshop.webshop.repository.ProductRepository;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductApiController {
    private ProductRepository productRepository;
    public ProductApiController(ProductRepository productRepository){this.productRepository = productRepository;}

}
