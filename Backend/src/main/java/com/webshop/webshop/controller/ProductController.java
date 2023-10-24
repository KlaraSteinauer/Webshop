package com.webshop.webshop.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.webshop.webshop.DTO.ProductViewDTO;
import com.webshop.webshop.model.Product;
import com.webshop.webshop.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

    @Autowired
    private Environment environment;

    @Value("${file.upload-dir}")
    public String IMAGE_PATH;


    /**
     * Creates a new Product including file upload.
     *
     * @param productJson JSON holding Product information
     * @param file image file
     * @return Product
     * @throws IOException
     */
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ProductViewDTO> createProductWithFile(
            @RequestPart("product") @Valid String productJson,
            @RequestPart("productImage") MultipartFile file) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        ProductViewDTO productViewDTO = objectMapper.readValue(productJson, ProductViewDTO.class);
        productViewDTO.setImageUrl(file.getOriginalFilename());
        File convertFile = new File(IMAGE_PATH + "/" + file.getOriginalFilename());
        System.out.println(convertFile.getAbsolutePath());
        convertFile.createNewFile();
        try (FileOutputStream fout = new FileOutputStream(convertFile)) {
            fout.write(file.getBytes());
        }
        return new ResponseEntity<ProductViewDTO>(productService.save(productViewDTO), HttpStatus.CREATED);
    }

    /**
     * Removes a Product from the Database.
     *
     * @param id product id
     * @return status message
     */
    @DeleteMapping("/{id}")// deletes a product (ID)
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        try {
            boolean deleted = productService.deleteById(id);
            String msg = "Product with id:  " + id + " deleted"
                    + (deleted == true ? " (file removed)." : ".");
            return new ResponseEntity<>(msg, HttpStatus.OK);
        } catch (ObjectNotFoundException e) {
            String msg = "Product with id: " + id + " not found.";
            return new ResponseEntity<>(msg, HttpStatus.NOT_FOUND);
        }

    }

    /**
     * Updates a product.
     *
     * @param productJson JSON holding Product information
     * @param file image file
     * @param itemId id of Product to be updated
     * @return Product
     * @throws IOException
     */
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

    /**
     * Fetches all Products from the Database.
     *
     * @return List of all Products
     */
    @GetMapping
    public ResponseEntity<List<ProductViewDTO>> getAllProducts() {

        return new ResponseEntity<>(productService.getAllProducts()
                .stream()
                .map(Product::convertToViewDto)
                .toList(),
                HttpStatus.OK);
    }

    /**
     * Fetches Product from the Database.
     *
     * @param id product id
     * @return Product
     * @throws ObjectNotFoundException
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProductViewDTO> findById(@PathVariable Long id) throws ObjectNotFoundException {
        try {
            return new ResponseEntity<>(productService.findById(id).convertToViewDto(), HttpStatus.OK);
        } catch (ObjectNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Fetches all Products of a Category from the Database.
     *
     * @param category ProductCategory
     * @return List of Products of a given category
     */
    @GetMapping("/category/{category}")
    public ResponseEntity<List<ProductViewDTO>> findByCategory(@PathVariable String category) {
        return new ResponseEntity<>(productService.findByCategory(category)
                .stream()
                .map(Product::convertToViewDto)
                .toList(),
                HttpStatus.OK);
    }



}
