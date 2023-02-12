package com.webshop.webshop.controller;

import com.webshop.webshop.model.Product;
import com.webshop.webshop.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

//Controller erhält den Request vom Frontend und sendet die Informationen weiter an das Service.
@RestController
@RequestMapping("/product")
public class ProductController {
    private ProductService productService;

    private ProductController(ProductService productService) {
        this.productService = productService;
    }

    //POST METHOD

    /*{
            "productName":"Salz",
            "productDescription":"Das ist Salz.",
            "productImageUrl":"IMAGE URL",
            "productPrice":"0.99",
            "productQuantity":5,
            "productCategory":"Salz&Pfeffer"
    }*/
    @PostMapping // creates a new product (JSON)
    public ResponseEntity<Product> createProduct(@RequestBody @Valid Product product) {
        product = productService.save(product);
        return ResponseEntity.created(URI.create("http://localhost:8080/product")).body(product);
    }

    // TODO try,catch
    //DELETE METHOD
    @DeleteMapping("/{id}")// deletes a product (ID)
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        //try {

        //}    //404 file not found
        productService.deleteById(id);

        String msg = "Product " + id + " deleted.";
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    //PUT METHOD
    @PutMapping("/{id}") // updates a product (ID)
    public ResponseEntity<Product> updateProductById(@RequestBody Product product, @PathVariable Long id) {

        Product updatedProduct = productService.update(id, product);

        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    }


    //GET METHOD

    // GET:     http://localhost:8080/product
    @GetMapping // shows all products
    public ResponseEntity<List<Product>> getAllProducts() {
        // calls the service to get all products
        List<Product> allProducts = productService.getAllProducts();
        // wraps the found products as a list in a ResponseEntity with the status 200 ok!
        return new ResponseEntity<>(allProducts, HttpStatus.OK);
    }

    @GetMapping("/findById/{id}") // filter by id
    public ResponseEntity<Product> findById(@PathVariable Long id) {
        Product p = productService.findById(id).get();
        return new ResponseEntity<>(p, HttpStatus.OK);
    }

    @GetMapping("/findByCategory/{category}")
    public ResponseEntity<List<Product>> findByCategory(@PathVariable String category) {
        List<Product> categoryProducts = productService.findByCategory(category);
        return new ResponseEntity<>(categoryProducts, HttpStatus.OK);
    }

    @GetMapping("/findByDescription/{description}") // filter by description
    public ResponseEntity<List<Product>> findByType(@PathVariable String description) {
        // calls the service to get all products
        List<Product> typeProducts = productService.findByType(description);
        // wraps the found products as a list in a ResponseEntity with the status 200 ok!
        return new ResponseEntity<>(typeProducts, HttpStatus.OK);
    }

    @GetMapping("/findByName/{name}") // filter by name (or part of name)
    public ResponseEntity<List<Product>> findByName(@PathVariable String name) {
        List<Product> matchingProducts = productService.findByName(name);
        return new ResponseEntity(matchingProducts, HttpStatus.OK);
    }
}


/*
    // http://localhost:8080/product/caveman
    //@ResponseStatus(HttpStatus.OK)
    @GetMapping("/caveman") // shows all products (caveman-style)
    public List<Product> readAll(){
        return productService.getAllProducts();
    }

     */
