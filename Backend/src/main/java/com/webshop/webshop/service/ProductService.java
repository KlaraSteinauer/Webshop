package com.webshop.webshop.service;

import com.webshop.webshop.DTO.ProductDTO;
import com.webshop.webshop.enums.ProductCategory;
import com.webshop.webshop.model.Product;
import com.webshop.webshop.repository.ProductRepository;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

//Enthält die Logik und darf mit dem Repository direkt kontaktieren um die CRUD-Funktionen aufzurufen.
//Hier werden auch die DTO in Model umgewandelt und umgekehrt! (mithilfe des Mappers)

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    public ProductDTO save(ProductDTO productDTO) {
        Product product = productDTO.convertToProduct();
        productRepository.save(product);
        return productDTO;
    }

    public ProductDTO update(Long id, ProductDTO updateProductDTO) throws ObjectNotFoundException {
        Product product = findById(id);
            product.setName(updateProductDTO.getName());
            product.setDescription(updateProductDTO.getDescription());
            product.setImageUrl(updateProductDTO.getImageUrl());
            product.setPrice(updateProductDTO.getPrice());
            product.setQuantity(updateProductDTO.getQuantity());
            product.setCategory(ProductCategory.valueOf(updateProductDTO.getCategory()));
            productRepository.save(product);
        return product.convertToDto();
    }


    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }


    public List<Product> findByDescription(String description) {
        return productRepository.findByDescription(description);
    }

    public List<Product> findByCategory(String category) {
        return productRepository.findByCategory(category);
    }

    public Product findById(Long id) throws ObjectNotFoundException {
        var product = productRepository.findById(id);
        if (product.isEmpty()) {
            throw new ObjectNotFoundException(product, "Product not found.");
        }
        return product.get();
    }

    @Deprecated
    public List<Product> findByLetter(String letter) {
        return productRepository.findAll();
    }
}