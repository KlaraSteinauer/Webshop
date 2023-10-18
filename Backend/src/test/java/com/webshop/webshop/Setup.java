package com.webshop.webshop;

import com.webshop.webshop.enums.ProductCategory;
import com.webshop.webshop.enums.Role;
import com.webshop.webshop.model.KimUser;
import com.webshop.webshop.model.Product;
import com.webshop.webshop.repository.KimUserRepository;
import com.webshop.webshop.repository.ProductRepository;
import com.webshop.webshop.service.TokenService;
import org.jose4j.jwt.GeneralJwtException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class Setup {


    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private KimUserRepository kimUserRepository;

    @Autowired
    private TokenService tokenService;

    private String token = "";

    public Setup(ProductRepository productRepository, KimUserRepository kimUserRepository, TokenService tokenService) {
        super();
    }

    public void clear() {
        productRepository.deleteAll();
        kimUserRepository.deleteAll();
    }



    public void productSetup() throws GeneralJwtException {
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
    }

    public String generateToken () throws GeneralJwtException {
        var adminExists = kimUserRepository.findAll().stream()
                .filter(ku -> ku.getId() == 2L).findFirst();
        KimUser admin = new KimUser();
        if (adminExists.isEmpty()) {
            admin.setId(2L);
            admin.setUserName("admin");
            admin.setUserPassword("adminPassword");
            admin.setUserEmail("admin@email.com");
            admin.setRole(Role.ADMIN);
            admin.setGender("male");
            admin.setFirstName("adminFirst");
            admin.setLastName("adminLast");
            kimUserRepository.save(admin);
        } else {
            admin = adminExists.get();
        }
        this.token = tokenService.generateToken(admin);
        return this.token;
    }



    public void userSetup() {
        KimUser customer = new KimUser();
        customer.setId(1L);
        customer.setUserName("customer");
        customer.setUserPassword("customerPassword");
        customer.setUserEmail("customer@email.com");
        customer.setRole(Role.CUSTOMER);
        customer.setGender("female");
        customer.setFirstName("customerFirst");
        customer.setLastName("customerLast");
        KimUser admin = new KimUser();
        admin.setId(2L);
        admin.setUserName("admin");
        admin.setUserPassword("adminPassword");
        admin.setUserEmail("admin@email.com");
        admin.setRole(Role.ADMIN);
        admin.setGender("male");
        admin.setFirstName("adminFirst");
        admin.setLastName("adminLast");
        KimUser anonymous = new KimUser();
        anonymous.setId(3L);
        anonymous.setUserName("anonymous");
        anonymous.setUserPassword("anonymousPassword");
        anonymous.setUserEmail("anonymous@email.com");
        anonymous.setRole(Role.ANONYMOUS);
        anonymous.setGender("non-binary");
        anonymous.setFirstName("anonymousFirst");
        anonymous.setLastName("anonymousLast");
        kimUserRepository.saveAll(List.of(customer, admin, anonymous));
    }

}
