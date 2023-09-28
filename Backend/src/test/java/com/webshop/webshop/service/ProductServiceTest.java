package com.webshop.webshop.service;

import com.webshop.webshop.DTO.ProductDTO;
import com.webshop.webshop.WebshopApplication;
import com.webshop.webshop.enums.ProductCategory;
import com.webshop.webshop.model.Product;
import com.webshop.webshop.repository.ProductRepository;
import com.webshop.webshop.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.Assert.assertEquals;


@SpringBootTest(classes = WebshopApplication.class)
@ActiveProfiles("test")
class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @BeforeEach
    void setup() {
        Product product1 = new Product();
        product1.setName("Product 1");
        product1.setDescription("This is Product 1");
        product1.setImageUrl("This is an image URL for Product 1");
        product1.setPrice(1);
        product1.setQuantity(1);
        product1.setCategory(ProductCategory.SALZPFEFFER);
        Product product2 = new Product();
        product2.setName("Product 2");
        product2.setDescription("This is Product 2");
        product2.setImageUrl("This is an image URL for Product 2");
        product2.setPrice(2);
        product2.setQuantity(2);
        product2.setCategory(ProductCategory.KRAEUTER);
        Product product3 = new Product();
        product3.setName("Product 3");
        product3.setDescription("This is Product 3");
        product3.setImageUrl("This is an image URL for Product 3");
        product3.setPrice(3);
        product3.setQuantity(3);
        product3.setCategory(ProductCategory.SUESSMITTEL);
        productRepository.saveAll(List.of(product1, product2, product3));
    }

    @Test
    void convertToDtoTest() {
        Product productToConvert = productRepository.findAll().stream()
                .filter(p -> p.getName().equals("Product 1"))
                .findFirst()
                .get();
        ProductDTO convertedProduct = productToConvert.convertToDto();
        assertAll(
            () -> assertEquals(productToConvert.getId(), convertedProduct.getId()),
            () -> assertEquals(productToConvert.getName(), convertedProduct.getName()),
            () -> assertEquals(productToConvert.getDescription(), convertedProduct.getDescription()),
            () -> assertEquals(productToConvert.getImageUrl(), convertedProduct.getImageUrl()),
            () -> assertEquals(productToConvert.getPrice(), convertedProduct.getPrice(), 0.1),
            () -> assertEquals(productToConvert.getQuantity(), convertedProduct.getQuantity()),
            () -> assertEquals(productToConvert.getCategory().toString(), convertedProduct.getCategory())
        );
    }

    @Test
    void saveTest() {
        assertEquals(3, productRepository.findAll().size());
        Product productToSave = new Product();
        productToSave.setName("Product to save");
        productToSave.setDescription("This is a Product to be saved");
        productToSave.setImageUrl("This is an image URL for the Product i want to save");
        productToSave.setPrice(99);
        productToSave.setQuantity(99);
        productToSave.setCategory(ProductCategory.SALZPFEFFER);
        productService.save(productToSave.convertToDto());
        assertEquals(4, productRepository.findAll().size());
    }
}
