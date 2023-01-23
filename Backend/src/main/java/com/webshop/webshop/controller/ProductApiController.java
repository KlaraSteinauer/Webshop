package com.webshop.webshop.controller;

import com.webshop.webshop.model.Product;
import com.webshop.webshop.repository.ProductRepository;
import com.webshop.webshop.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
//Controller erhält den Request vom Frontend und sendet die Informationen weiter an das Service.
@RestController
@RequestMapping("/product")
public class ProductApiController {
    private ProductService productService;
    public ProductApiController(ProductService productService){
        this.productService = productService;
    }

    @GetMapping("/allProducts")
    public ResponseEntity<List<Product>> getAllProducts() {
        // calls the service to get all the products
        List<Product> allProducts = productService.getAllProducts();
        // wraps the found products as a list in a ResponseEntity with the status 200 ok!
        return new ResponseEntity<>(allProducts, HttpStatus.OK);
    }


}
