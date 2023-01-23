package com.webshop.webshop.service;

import com.webshop.webshop.model.Product;
import com.webshop.webshop.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

//Enthält die Logik und darf mit dem Repository direkt kontaktieren um die CRUD-Funktionen aufzurufen.
//Hier werden auch die DTO in Model umgewandelt und umgekehrt! (mithilfe des Mappers)

@Service
public class ProductService {

    private ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        return (List<Product>) productRepository.findAll();
    }

}