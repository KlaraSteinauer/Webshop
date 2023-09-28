package com.webshop.webshop.controller;

import com.webshop.webshop.DTO.ProductDTO;
import com.webshop.webshop.model.Product;
import com.webshop.webshop.service.ProductService;
import org.hibernate.ObjectNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//Controller: receives the request information from frontend and sends it to service for further action.
@RestController
@RequestMapping("/product")
public record ProductController(ProductService productService) {

    @PostMapping
    public ResponseEntity<ProductDTO> createProduct(@RequestBody ProductDTO productDTO) {
        try{
        return new ResponseEntity<ProductDTO>(productService.save(productDTO), HttpStatus.CREATED);}
        catch (Exception e){
            return new ResponseEntity<ProductDTO>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")// deletes a product (ID)
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        productService.deleteById(id);
        String msg = "Product " + id + " deleted.";
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> updateProductById(@RequestBody ProductDTO productDTO, @PathVariable Long id) {
        ProductDTO updatedProduct = productService.update(id, productDTO);
        if (updatedProduct == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        return new ResponseEntity<>(productService.getAllProducts()
                .stream()
                .map(Product::convertToDto)
                .toList(),
                HttpStatus.OK);
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<ProductDTO> findById(@PathVariable Long id) throws ObjectNotFoundException {
        return new ResponseEntity<>(productService.findById(id).convertToDto(), HttpStatus.OK);
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
