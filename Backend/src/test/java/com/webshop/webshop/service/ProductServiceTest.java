package com.webshop.webshop.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.webshop.webshop.DTO.ProductViewDTO;
import com.webshop.webshop.WebshopApplication;
import com.webshop.webshop.enums.ProductCategory;
import com.webshop.webshop.model.Product;
import com.webshop.webshop.repository.ProductRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItem;
import org.hibernate.ObjectNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static java.nio.file.StandardCopyOption.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;


@SpringBootTest(classes = WebshopApplication.class)
@ActiveProfiles("test")
class ProductServiceTest {

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

    @AfterEach
    void tearDown() {
        File sourceFolder = new File(IMAGE_TEST_PATH);
        for (File file : sourceFolder.listFiles()) {
            file.delete();
        }
        productRepository.deleteAll();
    }


    /**
     * Converts Product to PrductDTO.
     */
    @Test
    void convertToDtoTest() {
        final Product productToConvert = productRepository.findAll().stream()
                .filter(p -> p.getName().equals("Product 1"))
                .findFirst()
                .get();
        ProductViewDTO convertedProduct = assertDoesNotThrow(() -> productToConvert.convertToViewDto());
        assertAll(
                () -> assertEquals(productToConvert.getId(), convertedProduct.getId()),
                () -> assertEquals(productToConvert.getName(), convertedProduct.getName()),
                () -> assertEquals(productToConvert.getDescription(), convertedProduct.getDescription()),
                () -> assertEquals(productToConvert.getImageUrl(), convertedProduct.getImageUrl()),
                () -> assertEquals(productToConvert.getPrice(), convertedProduct.getPrice(), 0.1),
                () -> assertEquals(productToConvert.getQuantity(), convertedProduct.getQuantity()),
                () -> assertEquals(productToConvert.getCategory().name(), convertedProduct.getCategory())
        );
    }


    /**
     * Saves a Product.
     */
    @Test
    void saveTest() {
        assertEquals(3, productRepository.findAll().size());
        Product productToSave = new Product();
        productToSave.setName("Product to save");
        productToSave.setDescription("This is a Product to be saved");
        productToSave.setImageUrl("black-pepper.jpg");
        productToSave.setPrice(99);
        productToSave.setQuantity(99);
        productToSave.setCategory(ProductCategory.SALZPFEFFER);
        ProductViewDTO savedProduct = assertDoesNotThrow(() -> productService.save(productToSave.convertToViewDto()));
        assertEquals(4, productRepository.findAll().size());
    }


    @Test
    void updateTest() throws IOException {
        final Product productToUpdate = productRepository.findAll().stream()
                .filter(p -> p.getName().equals("Product 1"))
                .findFirst()
                .get();
        final Long productId = productToUpdate.getId();
        final Long wrongId = 123456L;
        File file = new File(IMAGE_TEST_PATH + "/" + "brown-sugar.jpg");
        System.out.println("FILE_NAME: " + file.getName());
        InputStream input = new FileInputStream(file);
        MultipartFile multipartFile = new MockMultipartFile(file.getName(), file.getName(), null, input);
        System.out.println("MULTIPARTFILE_NAME: " + multipartFile.getOriginalFilename());
        ProductViewDTO updateDTO = new ProductViewDTO();
        updateDTO.setName("new Name");
        updateDTO.setDescription("new Description");
        updateDTO.setImageUrl("brown-sugar.jpg");
        updateDTO.setPrice(10);
        updateDTO.setQuantity(10);
        updateDTO.setCategory(ProductCategory.SUESSMITTEL.name());


        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(updateDTO);
        assertThrows(ObjectNotFoundException.class,
                () -> productService.update(wrongId, json, multipartFile));
        // make sure no new products were generated
        assertEquals(3, productRepository.findAll().size());
        ProductViewDTO updatedProduct = assertDoesNotThrow(() -> productService.update(productId, json, multipartFile));
        assertAll(
                () -> assertEquals(productId, updatedProduct.getId()),
                () -> assertEquals(updateDTO.getName(), updatedProduct.getName()),
                () -> assertEquals(updateDTO.getDescription(), updatedProduct.getDescription()),
                () -> assertEquals(updateDTO.getImageUrl(), updatedProduct.getImageUrl()),
                () -> assertEquals(updateDTO.getPrice(), updatedProduct.getPrice(), 0.1),
                () -> assertEquals(updateDTO.getQuantity(), updatedProduct.getQuantity()),
                () -> assertEquals(updateDTO.getCategory(), updatedProduct.getCategory())
        );
        // make sure no new products were generated
        assertEquals(3, productRepository.findAll().size());
    }


    @Test
    void deleteByIdTest() {
        final Product productToUpdate = productRepository.findAll().stream()
                .filter(p -> p.getName().equals("Product 1"))
                .findFirst()
                .get();
        final Long idToDelete = productToUpdate.getId();
        final Long wrongId = 123456L;
        assertThrows(ObjectNotFoundException.class,
                () -> productService.deleteById(wrongId));
        // make sure no products were deleted
        assertEquals(3, productRepository.findAll().size());
        assertDoesNotThrow(() -> productService.deleteById(idToDelete));
        // only 2 products remain
        assertEquals(2, productRepository.findAll().size());
    }

    @Test
    void findByIdTest() {
        final Product productToUpdate = productRepository.findAll().stream()
                .filter(p -> p.getName().equals("Product 1"))
                .findFirst()
                .get();
        final Long productId = productToUpdate.getId();
        final Long wrongId = 123456L;
        assertThrows(ObjectNotFoundException.class, () -> productService.findById(wrongId));
        Product foundProduct = assertDoesNotThrow(() -> productService.findById(productId));
        assertEquals(productToUpdate.getId(), foundProduct.getId());
        assertEquals(productToUpdate.getName(), foundProduct.getName());
        assertEquals(productToUpdate.getDescription(), foundProduct.getDescription());
        assertEquals(productToUpdate.getImageUrl(), foundProduct.getImageUrl());
        assertEquals(productToUpdate.getPrice(), foundProduct.getPrice(), 0.1);
        assertEquals(productToUpdate.getQuantity(), foundProduct.getQuantity());
        assertEquals(productToUpdate.getCategory(), foundProduct.getCategory());
    }


}
