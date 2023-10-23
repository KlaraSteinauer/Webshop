package com.webshop.webshop.service;

import com.webshop.webshop.WebshopApplication;
import com.webshop.webshop.enums.ProductCategory;
import com.webshop.webshop.model.Product;
import com.webshop.webshop.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;


@SpringBootTest(classes = WebshopApplication.class)
@ActiveProfiles("test")
public class PositionServiceTest {


    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @Value("${file.upload-dir}")
    public String IMAGE_TEST_PATH;

    @Value("${file.test-dir}")
    public String IMAGE_PATH;


    @BeforeEach
    void setup() throws IOException {
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
        productRepository.saveAll(List.of(product1, product2, product3));
    }
}
