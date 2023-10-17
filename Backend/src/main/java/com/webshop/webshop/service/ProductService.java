package com.webshop.webshop.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.webshop.webshop.DTO.ProductDTO;
import com.webshop.webshop.enums.ProductCategory;
import com.webshop.webshop.model.Product;
import com.webshop.webshop.repository.ProductRepository;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import static com.webshop.webshop.controller.ProductController.IMAGE_PATH;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    public ProductDTO save(ProductDTO productDTO) {
        Product product = productDTO.convertToProduct();
        Product savedProduct = productRepository.save(product);
        return savedProduct.convertToDto();
    }

    public ProductDTO update(Long id, String productJson, MultipartFile file) throws ObjectNotFoundException, IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        ProductDTO productDTO = objectMapper.readValue(productJson, ProductDTO.class);
        String filePath = IMAGE_PATH;
        File convertFile = new File(IMAGE_PATH + file.getOriginalFilename());
        if (!convertFile.getParentFile().exists()) {
            convertFile.getParentFile().mkdirs();
        }
        convertFile.createNewFile();
        try (FileOutputStream fout = new FileOutputStream(convertFile)) {
            fout.write(file.getBytes());
        }

        Product product = findById(id);
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setImageUrl(file.getOriginalFilename());
        product.setPrice(productDTO.getPrice());
        product.setQuantity(productDTO.getQuantity());
        product.setCategory(ProductCategory.valueOf(productDTO.getCategory()));
        productRepository.save(product);
        return product.convertToDto();
    }


    public void deleteById(Long id) {
        try {
            productRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ObjectNotFoundException(Product.class, "Product with id: " + id + "not found!");
        }
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public List<Product> findByDescription(String description) {
        return productRepository.findByDescription(description);
    }


    public List<Product> findByCategory(String category) {
        try {
            return productRepository.findByCategory(ProductCategory.valueOf(category));
        } catch (InvalidDataAccessApiUsageException e) {
            throw new IllegalArgumentException(category + " is no valid product category!");
        }
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