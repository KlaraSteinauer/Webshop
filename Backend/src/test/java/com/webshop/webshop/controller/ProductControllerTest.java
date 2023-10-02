package com.webshop.webshop.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.webshop.webshop.DTO.ProductDTO;
import com.webshop.webshop.enums.ProductCategory;
import com.webshop.webshop.model.Product;
import com.webshop.webshop.repository.ProductRepository;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.springframework.http.RequestEntity.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@ActiveProfiles("test")
@AutoConfigureDataJpa
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ExtendWith(SpringExtension.class)
@ComponentScan("com.webshop.webshop.controller")
@ComponentScan("com.webshop.webshop.service")
@ComponentScan("com.webshop.webshop.repository")
@ComponentScan("com.webshop.webshop.config")
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private ProductRepository productRepository;


    @BeforeEach
    void setup() {
        productRepository.deleteAll();
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
    void createProductTest() throws Exception {
        ProductDTO responseBody = new ProductDTO();
        responseBody.setName("newProductName");
        responseBody.setDescription("newProductDescription");
        responseBody.setImageUrl("newProductImageURL");
        responseBody.setPrice(64);
        responseBody.setQuantity(64);
        responseBody.setCategory(ProductCategory.SALZPFEFFER.name());
        final ProductDTO emptyBody = new ProductDTO();

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/product")
                        .content(mapper.writeValueAsString(emptyBody))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/product")
                            .content(mapper.writeValueAsString(responseBody))
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name", Matchers.containsString("newProductName")));
    }

    @Test
    void deleteProductTest() throws Exception {
        final Product product = productRepository.findAll().stream()
                .filter(p -> p.getName().equals("Product 1"))
                .findFirst()
                .get();
        final Long productId = product.getId();
        final Long wrongId = 123456L;

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/product/{id}", wrongId))
                .andExpect(status().isNotFound());

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/product/{id}", productId))
                .andExpect(status().isOk());

    }

    @Test
    void updateProductByIdTest() throws Exception {
        ProductDTO responseBody = new ProductDTO();
        responseBody.setName("newProductName");
        responseBody.setDescription("newProductDescription");
        responseBody.setImageUrl("newProductImageURL");
        responseBody.setPrice(64);
        responseBody.setQuantity(64);
        responseBody.setCategory(ProductCategory.SALZPFEFFER.name());
        final Product product = productRepository.findAll().stream()
                .filter(p -> p.getName().equals("Product 1"))
                .findFirst()
                .get();
        final Long productId = product.getId();
        final ProductDTO emptyBody = new ProductDTO();
        final Long wrongId = 123456L;

        mockMvc.perform(MockMvcRequestBuilders.put("/product/{itemid}", wrongId)
                        .content(mapper.writeValueAsString(responseBody))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        mockMvc.perform(MockMvcRequestBuilders.put("/product/{itemid}", productId)
                        .content(mapper.writeValueAsString(emptyBody))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        mockMvc.perform(MockMvcRequestBuilders.put("/product/{itemid}", wrongId)
                        .content(mapper.writeValueAsString(emptyBody))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        mockMvc.perform(MockMvcRequestBuilders.put("/product/{itemid}", productId)
                        .content(mapper.writeValueAsString(responseBody))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name", Matchers.containsString("newProductName")));
    }

    @Test
    void getAllProductsTest() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/product/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", Matchers.is(3)))
                .andExpect(jsonPath("$.[*]").isNotEmpty());
    }

    @Test
    void findByIdTest() throws Exception {
        final Product product = productRepository.findAll().stream()
                .filter(p -> p.getName().equals("Product 1"))
                .findFirst()
                .get();
        final Long productId = product.getId();

        mockMvc.perform(MockMvcRequestBuilders.get("/product/findById/{id}", productId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name", Matchers.containsString(product.getName())));
    }

    @Test
    void findByCategoryTest() throws Exception {
        String category = ProductCategory.SALZPFEFFER.name();

        mockMvc.perform(MockMvcRequestBuilders.get("/product/findByCategory/{category}", category))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", Matchers.is(1)))
                .andExpect(jsonPath("$.[0].name", Matchers.containsString("Product 1")))
                .andExpect(jsonPath("$.[*]").isNotEmpty());
    }

    @Test
    void findByDescriptionTest() throws Exception {
        final String description = "This is Product 1";

        mockMvc.perform(MockMvcRequestBuilders.get("/product/findByDescription/{description}", description))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", Matchers.is(1)))
                .andExpect(jsonPath("$.[0].name", Matchers.containsString("Product 1")))
                .andExpect(jsonPath("$.[*]").isNotEmpty());
    }

    @Disabled
    @Test
    void findByLetterTest() {

    }


}
