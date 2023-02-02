package com.webshop.webshop.controller;

import com.webshop.webshop.model.Product;
import com.webshop.webshop.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

//Controller erhält den Request vom Frontend und sendet die Informationen weiter an das Service.
@RestController
@RequestMapping("/product")
public class ProductController {

    /*@Autowired
    private ProductRepository repo;

    @GetMapping
    public List<Product> findAllProducts() {
        return repo.findAll()

    @GetMapping("/{type}")
        public List<Product> findAllProductsByType (@PathVariable String type){
            return repo.findByType(type);
        }
     */

        @GetMapping("/{description}") //filter by description
        public ResponseEntity<List<Product>> findByType (@PathVariable String description){
            // calls the service to get all the products
            List<Product> typeProducts = productService.findByType(description);
            // wraps the found products as a list in a ResponseEntity with the status 200 ok!
            return new ResponseEntity<>(typeProducts, HttpStatus.OK);
        }
        @PostMapping //Endpunkt
        public ResponseEntity<Product> createProduct (@RequestBody Product product){
            product = productService.save(product);
            return ResponseEntity.created(URI.create("http://localhost:8080/product")).body(product);
        }


        private ProductService productService;
    public ProductController(ProductService productService) {
            this.productService = productService;
        }

        //http://localhost:8080/product/allProducts
        @GetMapping("/allProducts")
        public ResponseEntity<List<Product>> getAllProducts () {
            // calls the service to get all the products
            List<Product> allProducts = productService.getAllProducts();
            // wraps the found products as a list in a ResponseEntity with the status 200 ok!
            return new ResponseEntity<>(allProducts, HttpStatus.OK);
        }


    }