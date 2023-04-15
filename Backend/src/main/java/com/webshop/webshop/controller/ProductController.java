package com.webshop.webshop.controller;

import com.webshop.webshop.DTO.ProductDTO;
import com.webshop.webshop.model.Product;
import com.webshop.webshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//Controller: receives the request information from frontend and sends it to service for further action.
@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    private ProductController(ProductService productService) {
        this.productService = productService;
    }

    /*
     * Example: Request body as json
     * {
     * "productName":"Salz",
     * "productDescription":"Das ist Salz.",
     * "productImageUrl":"IMAGE URL",
     * "productPrice":"0.99",
     * "productQuantity":5,
     * "productCategory":"Salz&Pfeffer"
     * }
     */

    @PostMapping
    public ResponseEntity<ProductDTO> createProduct(@RequestBody Product product) {
        /*
         * ResponseEntity is the typical http response, which usually contains the body (json, xml or whatsoever..)
         * and response code (200 OK, 400 bad request, 404 unauthorized usw...)
         */
        return new ResponseEntity<>(productService.save(product), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")// deletes a product (ID)
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        productService.deleteById(id);
        String msg = "Product " + id + " deleted.";
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> updateProductById(@RequestBody Product product, @PathVariable Long id) {
        ProductDTO updatedProduct = productService.update(id, product);
        if (updatedProduct == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        return new ResponseEntity<>(productService.getAllProducts(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> findById(@PathVariable Long id) {
        return new ResponseEntity<>(productService.findById(id), HttpStatus.OK);
    }

    @GetMapping("/findByCategory/{category}")
    public ResponseEntity<List<ProductDTO>> findByCategory(@PathVariable String category) {
        return new ResponseEntity<>(productService.findByCategory(category), HttpStatus.OK);
    }

    @GetMapping("/findByDescription/{description}")
    public ResponseEntity<List<ProductDTO>> findByType(@PathVariable String description) {
        return new ResponseEntity<>(productService.findByDescription(description), HttpStatus.OK);
    }

    @GetMapping("/findByName/{letter}")
    public ResponseEntity<List<ProductDTO>> findByLetter(@PathVariable String letter) {
        return new ResponseEntity<>(productService.findByLetter(letter), HttpStatus.OK);
    }

}
