package com.webshop.webshop.service;

import com.webshop.webshop.DTO.ProductDTO;
import com.webshop.webshop.WebshopApplication;
import com.webshop.webshop.enums.ProductCategory;
import com.webshop.webshop.enums.Role;
import com.webshop.webshop.model.Address;
import com.webshop.webshop.model.KimUser;
import com.webshop.webshop.model.Product;
import com.webshop.webshop.repository.KimUserRepository;
import com.webshop.webshop.repository.ProductRepository;
import com.webshop.webshop.security.UserDetails;
import org.h2.command.Token;
import org.hibernate.ObjectNotFoundException;
import org.jose4j.jwt.GeneralJwtException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest(classes = WebshopApplication.class)
@ActiveProfiles("test")
public class TokenServiceTest {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private KimUserRepository kimUserRepository;


    @BeforeEach
    void setup() {
        kimUserRepository.deleteAll();
        KimUser customer = new KimUser();
        customer.setUserId(1L);
        customer.setUserName("customer");
        customer.setUserPassword("customerPassword");
        customer.setEMail("customer@email.com");
        customer.setRole(Role.CUSTOMER);
        customer.setGender("female");
        customer.setFirstName("customerFirst");
        customer.setLastName("customerLast");
        KimUser admin = new KimUser();
        admin.setUserId(2L);
        admin.setUserName("admin");
        admin.setUserPassword("adminPassword");
        admin.setEMail("admin@email.com");
        admin.setRole(Role.ADMIN);
        admin.setGender("male");
        admin.setFirstName("adminFirst");
        admin.setLastName("adminLast");
        KimUser anonymous = new KimUser();
        anonymous.setUserId(3L);
        anonymous.setUserName("anonymous");
        anonymous.setUserPassword("anonymousPassword");
        anonymous.setEMail("anonymous@email.com");
        anonymous.setRole(Role.ANONYMOUS);
        anonymous.setGender("non-binary");
        anonymous.setFirstName("anonymousFirst");
        anonymous.setLastName("anonymousLast");
        kimUserRepository.saveAll(List.of(customer, admin, anonymous));
    }

    @Test
    void generateTokenTest() throws GeneralJwtException {
        final KimUser customer = kimUserRepository.findAll().stream()
                .filter(u -> u.getUserName().equals("customer"))
                .findFirst()
                .get();
        final String customerToken = assertDoesNotThrow(() -> tokenService.generateToken(customer));
        KimUser wrongCustomer = customer;
        wrongCustomer.setUserName(null);
        assertThrows(IllegalArgumentException.class,
                () -> tokenService.generateToken(wrongCustomer));
        assertEquals(145, customerToken.length());
    }

    @Test
    void parseTokenTest() {
        final KimUser customer = kimUserRepository.findAll().stream()
                .filter(u -> u.getUserName().equals("customer"))
                .findFirst()
                .get();
        final String customerToken = assertDoesNotThrow(() -> tokenService.generateToken(customer));
        Optional<UserDetails> var = assertDoesNotThrow(() -> tokenService.parseToken(customerToken));
        UserDetails userDetails =  assertDoesNotThrow(() -> var.get());
        assertAll(
                () -> assertEquals(customer.getUserId(), userDetails.getUserId()),
                () -> assertEquals(customer.getUserName(), userDetails.getUserName()),
                () -> assertEquals(customer.getRole(), userDetails.getUserRole())
        );
    }


    @Disabled
    @Test
    void validateTokenTest() {

    }


    @Disabled
    @Test
    void getClaimsFromTokenTest() {

    }

}
