package com.webshop.webshop.controller;

import com.webshop.webshop.DTO.ProductDTO;
import com.webshop.webshop.DTO.ProductFileDTO;
import com.webshop.webshop.model.Product;
import com.webshop.webshop.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.hibernate.ObjectNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    public static String IMAGE_PATH = "../Frontend/images/";

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ProductDTO> createProduct(@RequestBody ProductDTO productDTO) {
        try {
            return new ResponseEntity<ProductDTO>(productService.save(productDTO), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<ProductDTO>(HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/file")
    public ResponseEntity<ProductDTO> createProductWithFile(@RequestBody ProductFileDTO productFileDTO) throws IOException {
        MultipartFile file = productFileDTO.getImage();
        File convertFile = new File(IMAGE_PATH + productFileDTO.getImage().getOriginalFilename());
        convertFile.createNewFile();
        FileOutputStream fout = new FileOutputStream(convertFile);
        fout.write(file.getBytes());
        fout.close();
        //return new ResponseEntity<>("File upload successful!", HttpStatus.CREATED);


        try {
            return new ResponseEntity<ProductDTO>(productService.save(productFileDTO.convertToProductDto()), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<ProductDTO>(HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
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

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{itemId}")
    public ResponseEntity<ProductDTO> updateProductById(@RequestBody ProductDTO productDTO, @PathVariable Long itemId) {
        try {
            ProductDTO updatedProduct = productService.update(itemId, productDTO);
            return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/all")
    public ResponseEntity<List<ProductDTO>> getAllProducts() {

        return new ResponseEntity<>(productService.getAllProducts()
                .stream()
                .map(Product::convertToDto)
                .toList(),
                HttpStatus.OK);
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<ProductDTO> findById(@PathVariable Long id) throws ObjectNotFoundException {
        try {
            return new ResponseEntity<>(productService.findById(id).convertToDto(), HttpStatus.OK);
        } catch (ObjectNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/findByCategory/{category}")
    public ResponseEntity<List<ProductDTO>> findByCategory(@PathVariable String category) {
        return new ResponseEntity<>(productService.findByCategory(category)
                .stream()
                .map(Product::convertToDto)
                .toList(),
                HttpStatus.OK);
    }

    @GetMapping("/findByDescription/{description}")
    public ResponseEntity<List<ProductDTO>> findByType(@PathVariable String description) {
        return new ResponseEntity<>(productService.findByDescription(description).stream()
                .map(Product::convertToDto)
                .toList(),
                HttpStatus.OK);
    }

    @Deprecated
    @GetMapping("/findByName/{letter}")
    public ResponseEntity<List<ProductDTO>> findByLetter(@PathVariable String letter) {
        return new ResponseEntity<>(productService.findByLetter(letter).stream()
                .map(Product::convertToDto)
                .toList(),
                HttpStatus.OK);
    }

}
