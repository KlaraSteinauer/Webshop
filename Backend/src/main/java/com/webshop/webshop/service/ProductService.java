package com.webshop.webshop.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.webshop.webshop.DTO.ProductViewDTO;
import com.webshop.webshop.enums.ProductCategory;
import com.webshop.webshop.model.Product;
import com.webshop.webshop.repository.ProductRepository;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    @Value("${file.upload-dir}")
    public String IMAGE_PATH;

    public ProductViewDTO save(ProductViewDTO productViewDTO) {
        Product product = productViewDTO.convertToProduct();
        Product savedProduct = productRepository.save(product);
        return savedProduct.convertToViewDto();
    }


    public ProductViewDTO update(Long id, String productJson, MultipartFile file) throws ObjectNotFoundException, IOException {
        Product product = findById(id);
        ObjectMapper objectMapper = new ObjectMapper();
        ProductViewDTO productViewDTO = objectMapper.readValue(productJson, ProductViewDTO.class);
        File convertFile = new File(IMAGE_PATH + "/" + file.getOriginalFilename());
        /*if (!convertFile.getParentFile().exists()) {
            convertFile.getParentFile().mkdir();
        }
         */
        convertFile.createNewFile();
        try (FileOutputStream fout = new FileOutputStream(convertFile)) {
            fout.write(file.getBytes());
        }
        removeImage(productViewDTO.convertToProduct());

        product.setName(productViewDTO.getName());
        product.setDescription(productViewDTO.getDescription());
        product.setImageUrl(file.getOriginalFilename());
        product.setPrice(productViewDTO.getPrice());
        product.setQuantity(productViewDTO.getQuantity());
        product.setCategory(ProductCategory.valueOf(productViewDTO.getCategory()));
        productRepository.save(product);
        return product.convertToViewDto();
    }

    public boolean deleteById(Long id) {
        try {
            Product productToDelete = findById(id);
            boolean deleted = removeImage(productToDelete);
            productRepository.deleteById(id);
            return deleted;
        } catch (EmptyResultDataAccessException e) {
            throw new ObjectNotFoundException(Product.class, "Product with id: " + id + "not found!");
        }
    }

    public boolean removeImage(Product product) {
        List<Product> products = productRepository.findAll();
        boolean isUsed = false;
        boolean deleted = false;
        for (Product p : products) {
            if (p.getId() != product.getId() && p.getImageUrl().equals(product.getImageUrl())) {
            isUsed = true;
            }
        }
        if (!isUsed) {
            File fileToDelete = new File(IMAGE_PATH + "/" + product.getImageUrl());
            deleted = fileToDelete.delete();
        }
        return deleted;
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
}