package com.webshop.webshop.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.webshop.webshop.DTO.ProductViewDTO;
import com.webshop.webshop.enums.ProductCategory;
import com.webshop.webshop.enums.Role;
import com.webshop.webshop.model.KimUser;
import com.webshop.webshop.model.Product;
import com.webshop.webshop.repository.KimUserRepository;
import com.webshop.webshop.repository.ProductRepository;
import com.webshop.webshop.service.TokenService;
import org.hamcrest.Matchers;
import org.jose4j.jwt.GeneralJwtException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.File;
import java.io.IOException;
import java.util.List;

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

    @Autowired
    private KimUserRepository kimUserRepository;

    @Autowired
    private TokenService tokenService;

    @Value("${file.upload-dir}")
    public String IMAGE_PATH;

    private String token = "";


    @BeforeEach
    void setup() throws GeneralJwtException {
        productRepository.deleteAll();
        kimUserRepository.deleteAll();
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
        KimUser admin = new KimUser();
        admin.setId(2L);
        admin.setUserName("admin");
        admin.setUserPassword("adminPassword");
        admin.setUserEmail("admin@email.com");
        admin.setRole(Role.ADMIN);
        admin.setGender("male");
        admin.setFirstName("adminFirst");
        admin.setLastName("adminLast");
        productRepository.saveAll(List.of(product1, product2, product3));
        kimUserRepository.save(admin);
        token = tokenService.generateToken(admin);
    }


    @Disabled
    @Test
    void createProductTest() throws Exception {
        ProductViewDTO responseBody = new ProductViewDTO();
        responseBody.setName("newProductName");
        responseBody.setDescription("newProductDescription");
        responseBody.setImageUrl("newProductImageURL");
        responseBody.setPrice(64);
        responseBody.setQuantity(64);
        responseBody.setCategory(ProductCategory.SALZPFEFFER.name());
        final ProductViewDTO emptyBody = new ProductViewDTO();

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/product")
                        .header("Authorization", "Bearer " + token)
                        .content(mapper.writeValueAsString(emptyBody))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/product")
                        .header("Authorization", "Bearer " + token)
                        .content(mapper.writeValueAsString(responseBody))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name", Matchers.containsString("newProductName")));
    }

    @Disabled
    @Test
    void deleteProductTest() throws Exception {
        final Product product = productRepository.findAll().stream()
                .filter(p -> p.getName().equals("Product 1"))
                .findFirst()
                .get();
        final Long productId = product.getId();
        final Long wrongId = 123456L;

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/product/{id}", wrongId)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isNotFound());

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/product/{id}", productId)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());

    }

    /*@Test
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
                        .header("Authorization", "Bearer " + token)
                        .content(mapper.writeValueAsString(responseBody))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        mockMvc.perform(MockMvcRequestBuilders.put("/product/{itemid}", productId)
                        .header("Authorization", "Bearer " + token)
                        .content(mapper.writeValueAsString(emptyBody))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        mockMvc.perform(MockMvcRequestBuilders.put("/product/{itemid}", wrongId)
                        .header("Authorization", "Bearer " + token)
                        .content(mapper.writeValueAsString(emptyBody))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        mockMvc.perform(MockMvcRequestBuilders.put("/product/{itemid}", productId)
                        .header("Authorization", "Bearer " + token)
                        .content(mapper.writeValueAsString(responseBody))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name", Matchers.containsString("newProductName")));
    }*/

    @Disabled
    @Test
    void getAllProductsTest() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/product/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", Matchers.is(3)))
                .andExpect(jsonPath("$.[*]").isNotEmpty());
    }

    @Disabled
    @Test
    void findByIdTest() throws Exception {
        final Product product = productRepository.findAll().stream()
                .filter(p -> p.getName().equals("Product 1"))
                .findFirst()
                .get();
        final Long productId = product.getId();

        mockMvc.perform(MockMvcRequestBuilders.get("/product/findById/{id}", productId)
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name", Matchers.containsString(product.getName())));
    }

    @Disabled
    @Test
    void findByCategoryTest() throws Exception {
        String category = ProductCategory.SALZPFEFFER.name();

        mockMvc.perform(MockMvcRequestBuilders.get("/product/findByCategory/{category}", category))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", Matchers.is(1)))
                .andExpect(jsonPath("$.[0].name", Matchers.containsString("Product 1")))
                .andExpect(jsonPath("$.[*]").isNotEmpty());
    }

    @Disabled
    @Test
    void findByDescriptionTest() throws Exception {
        final String description = "This is Product 1";

        mockMvc.perform(MockMvcRequestBuilders.get("/product/findByDescription/{description}", description)
                        .header("Authorization", "Bearer" + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", Matchers.is(1)))
                .andExpect(jsonPath("$.[0].name", Matchers.containsString("Product 1")))
                .andExpect(jsonPath("$.[*]").isNotEmpty());
    }

}
