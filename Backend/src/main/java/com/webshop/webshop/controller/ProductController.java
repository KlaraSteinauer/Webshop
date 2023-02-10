package com.webshop.webshop.controller;

import com.webshop.webshop.model.Product;
import com.webshop.webshop.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

//Controller erhält den Request vom Frontend und sendet die Informationen weiter an das Service.
@RestController
@RequestMapping("/product")
public class ProductController {
    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    //POST METHOD
    @PostMapping // creates a new product (JSON)
    public ResponseEntity<Product> createProduct(@RequestBody @Valid Product product) {
        product = productService.save(product);
        return ResponseEntity.created(URI.create("http://localhost:8080/product")).body(product);
    }

    // TODO
    //DELETE METHOD
    @DeleteMapping("/{id}")// deletes a product (ID)
    public ResponseEntity<String> deleteProduct(@PathVariable Long id){
        //try {

        //}    //404 file not found
        productService.deleteById(id);

        String msg = "Product "+id+ " deleted.";
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }


    //PUT METHOD
    @PutMapping // updates a product (ID)
    public ResponseEntity<Product> updateProduct(Product product){

        return new ResponseEntity<>(product,HttpStatus.OK);
    }



    //GET METHOD

    // http://localhost:8080/product/all
    @GetMapping // shows all products
    public ResponseEntity<List<Product>> getAllProducts() {
        // calls the service to get all products
        List<Product> allProducts = productService.getAllProducts();
        // wraps the found products as a list in a ResponseEntity with the status 200 ok!
        return new ResponseEntity<>(allProducts,HttpStatus.OK);
    }



    @GetMapping("/{description}") // filter by description
    public ResponseEntity<List<Product>> findByType(@PathVariable String description) {
        // calls the service to get all products
        List<Product> typeProducts = productService.findByType(description);
        // wraps the found products as a list in a ResponseEntity with the status 200 ok!
        return new ResponseEntity<>(typeProducts, HttpStatus.OK);
    }

/*
    // http://localhost:8080/product/caveman
    //@ResponseStatus(HttpStatus.OK)
    @GetMapping("/caveman") // shows all products (caveman-style)
    public List<Product> readAll(){
        return productService.getAllProducts();
    }

     */
}