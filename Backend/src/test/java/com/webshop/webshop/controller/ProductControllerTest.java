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
import org.junit.jupiter.api.AfterEach;
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
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockPart;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.List;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
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
    public String IMAGE_TEST_PATH;

    @Value("${file.test-dir}")
    public String IMAGE_PATH;

    private String token = "";


    @BeforeEach
    void setup() throws GeneralJwtException, IOException {
        // copies all Images to test directory
        File sourceFolder = new File(IMAGE_PATH);
        File targetFolder = new File(IMAGE_TEST_PATH);
        if (!targetFolder.isDirectory()) {
            targetFolder.mkdirs();
        }
        for (File file : sourceFolder.listFiles()) {
            String fileName = file.getName();
            Files.copy(new File(IMAGE_PATH + "/" + fileName).toPath(), new File(IMAGE_TEST_PATH + "/" + fileName).toPath(), REPLACE_EXISTING);
        }
        Product product1 = new Product();
        product1.setName("Product 1");
        product1.setDescription("This is Product 1");
        product1.setImageUrl("black-pepper.jpg");
        product1.setPrice(1);
        product1.setQuantity(1);
        product1.setCategory(ProductCategory.SALZPFEFFER);
        Product product2 = new Product();
        product2.setName("Product 2");
        product2.setDescription("This is Product 2");
        product2.setImageUrl("cilantro.jpg");
        product2.setPrice(2);
        product2.setQuantity(2);
        product2.setCategory(ProductCategory.KRAEUTER);
        Product product3 = new Product();
        product3.setName("Product 3");
        product3.setDescription("This is Product 3");
        product3.setImageUrl("brown-sugar.jpg");
        product3.setPrice(3);
        product3.setQuantity(3);
        product3.setCategory(ProductCategory.SUESSMITTEL);
        KimUser admin = new KimUser();
        admin.setUserName("admin");
        // "admin" encrypted
        admin.setUserPassword("$2y$10$038vqDvwT4VTy9mhDy991OIgNcFJv9PcPaBVNKEzhufIE67nRkIiS");
        admin.setUserEmail("admin@admin.com");
        admin.setRole(Role.ADMIN);
        admin.setGender("admin");
        admin.setFirstName("admin");
        admin.setLastName("admin");
        productRepository.saveAll(List.of(product1, product2, product3));
        kimUserRepository.save(admin);
        token = tokenService.generateToken(admin);
    }

    @AfterEach
    void tearDown() {
        File sourceFolder = new File(IMAGE_TEST_PATH);
        for (File file : sourceFolder.listFiles()) {
            file.delete();
        }
        productRepository.deleteAll();
        kimUserRepository.deleteAll();
    }


    @Disabled
    @Test
    void createProductTest() throws Exception {
        ProductViewDTO responseBody = new ProductViewDTO();
        responseBody.setName("newProductName");
        responseBody.setDescription("newProductDescription");
        responseBody.setPrice(64);
        responseBody.setQuantity(64);
        responseBody.setCategory(ProductCategory.SALZPFEFFER.name());
        File file = new File(IMAGE_TEST_PATH + "/" + "mixed-pepper.jpg");
        System.out.println("FILE_NAME: " + file.getName());
        InputStream input = new FileInputStream(file);

        MockMultipartFile product = new MockMultipartFile("product", mapper.writeValueAsString(responseBody).getBytes());
        MockMultipartFile productImage = new MockMultipartFile(file.getName(), file.getName(), "File", input);
        System.out.println("MOCK_FILE_NAME: " + productImage.getOriginalFilename());
        /*MockPart product = new MockPart("product", mapper.writeValueAsString(responseBody).getBytes());
        MockPart productImage = new MockPart("productImage", multipartFile.getBytes());

         */
        final ProductViewDTO emptyBody = new ProductViewDTO();


        mockMvc.perform(MockMvcRequestBuilders
                        .multipart("/products")
                        .file(product)
                        .file(productImage)
                        .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                        .accept(MediaType.MULTIPART_FORM_DATA_VALUE)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isCreated());


        /*mockMvc.perform(MockMvcRequestBuilders
                        .post("/products")
                        .header("Authorization", "Bearer " + token)
                        .content(mapper.writeValueAsString(emptyBody))
                        .content(multipartFile.getBytes())
                        .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                        .accept(MediaType.MULTIPART_FORM_DATA_VALUE))
                .andExpect(status().isBadRequest());

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/products")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                        .content(mapper.writeValueAsString(responseBody))
                        .content(multipartFile.getBytes())
                        .accept(MediaType.MULTIPART_FORM_DATA_VALUE))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name", Matchers.containsString("newProductName")));

         */
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
                        .delete("/products/{id}", wrongId)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isNotFound());

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/products/{id}", productId)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());

    }

    @Disabled
    @Test
    void updateProductByIdTest() throws Exception {
        ProductViewDTO responseBody = new ProductViewDTO();
        responseBody.setName("newProductName");
        responseBody.setDescription("newProductDescription");
        responseBody.setPrice(64);
        responseBody.setQuantity(64);
        responseBody.setCategory(ProductCategory.SALZPFEFFER.name());
        final Product product = productRepository.findAll().stream()
                .filter(p -> p.getName().equals("Product 1"))
                .findFirst()
                .get();
        final Long productId = product.getId();
        final ProductViewDTO emptyBody = new ProductViewDTO();
        final Long wrongId = 123456L;

        mockMvc.perform(MockMvcRequestBuilders.put("/products/{id}", wrongId)
                        .header("Authorization", "Bearer " + token)
                        .content(mapper.writeValueAsString(responseBody))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        mockMvc.perform(MockMvcRequestBuilders.put("/products/{id}", productId)
                        .header("Authorization", "Bearer " + token)
                        .content(mapper.writeValueAsString(emptyBody))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        mockMvc.perform(MockMvcRequestBuilders.put("/products/{id}", wrongId)
                        .header("Authorization", "Bearer " + token)
                        .content(mapper.writeValueAsString(emptyBody))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        mockMvc.perform(MockMvcRequestBuilders.put("/products/{id}", productId)
                        .header("Authorization", "Bearer " + token)
                        .content(mapper.writeValueAsString(responseBody))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name", Matchers.containsString("newProductName")));
    }

    @Test
    void getAllProductsTest() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/products"))
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

        mockMvc.perform(MockMvcRequestBuilders.get("/products/{id}", productId)
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name", Matchers.containsString(product.getName())));
    }

    @Test
    void findByCategoryTest() throws Exception {
        String category = ProductCategory.SALZPFEFFER.name();

        mockMvc.perform(MockMvcRequestBuilders.get("/products/category/{category}", category))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", Matchers.is(1)))
                .andExpect(jsonPath("$.[0].name", Matchers.containsString("Product 1")))
                .andExpect(jsonPath("$.[*]").isNotEmpty());
    }
}
