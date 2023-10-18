package com.webshop.webshop.service;

import com.webshop.webshop.WebshopApplication;
import com.webshop.webshop.enums.Role;
import com.webshop.webshop.model.KimUser;
import com.webshop.webshop.repository.KimUserRepository;
import com.webshop.webshop.security.KimUserDetails;
import org.jose4j.jwt.GeneralJwtException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;

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
        // tests for token?
    }

    @Test
    void parseTokenTest() {
        final KimUser customer = kimUserRepository.findAll().stream()
                .filter(u -> u.getUserName().equals("customer"))
                .findFirst()
                .get();
        final String customerToken = assertDoesNotThrow(() -> tokenService.generateToken(customer));
        Optional<KimUserDetails> var = assertDoesNotThrow(() -> tokenService.parseToken(customerToken));
        KimUserDetails kimUserDetails = assertDoesNotThrow(() -> var.get());
        assertAll(
                () -> assertEquals(customer.getId(), kimUserDetails.getUserId()),
                () -> assertEquals(customer.getUserName(), kimUserDetails.getUserName()),
                () -> assertEquals(customer.getRole(), kimUserDetails.getUserRole())
        );
    }

    @Test
    void isAdminTest() {
        final KimUser customer = kimUserRepository.findAll().stream()
                .filter(u -> u.getUserName().equals("customer"))
                .findFirst()
                .get();
        final String customerToken = assertDoesNotThrow(() -> tokenService.generateToken(customer));
        Optional<KimUserDetails> var = assertDoesNotThrow(() -> tokenService.parseToken(customerToken));
        assertFalse(tokenService.isAdmin(customerToken));

        final KimUser admin = kimUserRepository.findAll().stream()
                .filter(u -> u.getUserName().equals("admin"))
                .findFirst()
                .get();
        final String adminToken = assertDoesNotThrow(() -> tokenService.generateToken(admin));
        var = assertDoesNotThrow(() -> tokenService.parseToken(adminToken));
        assertTrue(tokenService.isAdmin(adminToken));
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
