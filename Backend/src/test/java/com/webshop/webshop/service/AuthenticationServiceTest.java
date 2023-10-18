package com.webshop.webshop.service;

import com.webshop.webshop.WebshopApplication;
import com.webshop.webshop.enums.Role;
import com.webshop.webshop.model.KimUser;
import com.webshop.webshop.repository.KimUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest(classes = WebshopApplication.class)
@ActiveProfiles("test")
public class AuthenticationServiceTest {

    @Autowired
    private AuthenticationService authenticationService;

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

    @Disabled
    @Test
    void loginTest() {
        final KimUser customer = kimUserRepository.findAll().stream()
                .filter(u -> u.getUserName().equals("customer"))
                .findFirst()
                .get();
        final String userName = customer.getUserName();
        final String password = customer.getUserPassword();
        String token = assertDoesNotThrow(() -> authenticationService.login(userName, password));
        // tests for token?
    }
}
