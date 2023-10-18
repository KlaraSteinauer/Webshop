package com.webshop.webshop.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.webshop.webshop.DTO.ProductViewDTO;
import com.webshop.webshop.model.Product;
import com.webshop.webshop.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    @Autowired
    private final ProductService productService;

    public static String IMAGE_PATH = "../Frontend/images/";


    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ProductViewDTO> createProductWithFile(
            @RequestPart("product") @Valid String productJson,
            @RequestPart("productImage") MultipartFile file) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        ProductViewDTO productViewDTO = objectMapper.readValue(productJson, ProductViewDTO.class);
        productViewDTO.setImageUrl(file.getOriginalFilename());
        String filePath = IMAGE_PATH;
        File convertFile = new File(IMAGE_PATH + file.getOriginalFilename());
        if (!convertFile.getParentFile().exists()) {
            convertFile.getParentFile().mkdirs();
        }
        convertFile.createNewFile();
        try (FileOutputStream fout = new FileOutputStream(convertFile)) {
            fout.write(file.getBytes());
        }
        return new ResponseEntity<ProductViewDTO>(productService.save(productViewDTO), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")// deletes a product (ID)
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {

        Object authenticatedUser = SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();

        System.out.println(authenticatedUser);

        try {
            productService.deleteById(id);
            String msg = "Product " + id + " deleted.";
            return new ResponseEntity<>(msg, HttpStatus.OK);
        } catch (ObjectNotFoundException e) {
            String msg = "Product " + id + " not found.";
            return new ResponseEntity<>(msg, HttpStatus.NOT_FOUND);
        }

    }

    @PutMapping(path = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ProductViewDTO> updateProductById(
            @RequestPart("product") @Valid String productJson,
            @RequestPart("productImage") MultipartFile file,
            @PathVariable("id") Long itemId) throws IOException {

        try {
            ProductViewDTO updatedProduct = productService.update(itemId, productJson, file);
            return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }



    @GetMapping
    public ResponseEntity<List<ProductViewDTO>> getAllProducts() {

        return new ResponseEntity<>(productService.getAllProducts()
                .stream()
                .map(Product::convertToViewDto)
                .toList(),
                HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductViewDTO> findById(@PathVariable Long id) throws ObjectNotFoundException {
        try {
            return new ResponseEntity<>(productService.findById(id).convertToViewDto(), HttpStatus.OK);
        } catch (ObjectNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<ProductViewDTO>> findByCategory(@PathVariable String category) {
        return new ResponseEntity<>(productService.findByCategory(category)
                .stream()
                .map(Product::convertToViewDto)
                .toList(),
                HttpStatus.OK);
    }



}
