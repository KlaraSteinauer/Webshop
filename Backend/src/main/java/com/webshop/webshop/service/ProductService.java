package com.webshop.webshop.service;

import com.webshop.webshop.model.Product;
import com.webshop.webshop.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

//Enthält die Logik und darf mit dem Repository direkt kontaktieren um die CRUD-Funktionen aufzurufen.
//Hier werden auch die DTO in Model umgewandelt und umgekehrt! (mithilfe des Mappers)

@Service
public class ProductService {

    private ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product save(Product product) {
        //hier muss/sollte man nochmals validieren
        String name = product.getProductName();
        if (name == null || name.isBlank()) {
            throw new EntityNotFoundException();
        }
        return productRepository.save(product);
    }

    public Product update(Long id, Product product) {
        Product updatedProduct = findById(id).get(); // removes "Optional<>" and returns Product
        updatedProduct.setProductName(product.getProductName());
        updatedProduct.setProductDescription(product.getProductDescription());
        updatedProduct.setProductImageUrl(product.getProductImageUrl());
        updatedProduct.setProductPrice(product.getProductPrice());
        updatedProduct.setProductQuantity(product.getProductQuantity());
        updatedProduct.setProductCategory(product.getProductCategory());
        save(updatedProduct);
        return updatedProduct;
    }


    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    public List<Product> getAllProducts() {
        return (List<Product>) productRepository.findAll();
    }


    public List<Product> findByType(String description) {
        return productRepository.findByProductDescription(description);
    }

    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }


}