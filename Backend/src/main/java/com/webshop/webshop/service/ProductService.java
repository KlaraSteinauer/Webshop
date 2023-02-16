package com.webshop.webshop.service;

import com.webshop.webshop.model.Product;
import com.webshop.webshop.repository.ProductRepository;
import com.webshop.webshop.requestDTO.ProductDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

//Enthält die Logik und darf mit dem Repository direkt kontaktieren um die CRUD-Funktionen aufzurufen.
//Hier werden auch die DTO in Model umgewandelt und umgekehrt! (mithilfe des Mappers)

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductDTO save(Product product) {
        Product savedProduct = productRepository.save(product);
        return toDto(savedProduct);
    }

    public ProductDTO update(Long id, Product updateProduct) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            updateProduct.setProductName(product.getProductName());
            updateProduct.setProductDescription(product.getProductDescription());
            updateProduct.setProductImageUrl(product.getProductImageUrl());
            updateProduct.setProductPrice(product.getProductPrice());
            updateProduct.setProductQuantity(product.getProductQuantity());
            updateProduct.setProductCategory(product.getProductCategory());
        }
        Product updatedProduct = productRepository.save(updateProduct);
        return toDto(updatedProduct);
    }


    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    public List<ProductDTO> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return toDtos(products);
    }


    public List<ProductDTO> findByDescription(String description) {
        return toDtos(productRepository.findByProductDescription(description));
    }

    public List<ProductDTO> findByCategory(String category) {
        return toDtos(productRepository.findByProductCategory(category));
    }

    public ProductDTO findById(Long id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()) {
            return toDto(optionalProduct.get());
        }
        return null;
    }

    public List<ProductDTO> findByLetter(String letter) {
        List<Product> productList = productRepository.findAll().stream()
                .filter(product -> product.getProductName().contains(letter))
                .toList();
        return toDtos(productList);
    }

    private List<ProductDTO> toDtos(List<Product> products) {
        return products.stream()
                .map(this::toDto)
                .toList();
    }

    private ProductDTO toDto(Product data) {
        return new ProductDTO(data.getProductId(), data.getProductName(), data.getProductDescription(), data.getProductImageUrl(), data.getProductPrice(), data.getProductQuantity(), data.getProductCategory());
    }
}