package com.webshop.webshop.service;

import com.webshop.webshop.WebshopApplication;
import com.webshop.webshop.enums.Role;
import com.webshop.webshop.model.KimUser;
import com.webshop.webshop.repository.KimUserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.StringUtils;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = WebshopApplication.class)
@ActiveProfiles("test")
public class AuthenticationServiceTest {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private KimUserRepository kimUserRepository;

    /**
     * H2 Setup
     */
    @BeforeEach
    void setup() {
        KimUser customer = new KimUser();
        customer.setUserName("customer");
        // "customer" encrypted
        customer.setUserPassword("$2y$10$6/enuCKqS/fBSz6iIgfZC.XEmLmT2q9GuaKSz8dARHwOSmzEzN7Uq");
        customer.setUserEmail("customer@customer.com");
        customer.setRole(Role.CUSTOMER);
        customer.setGender("customer");
        customer.setFirstName("customer");
        customer.setLastName("customer");
        KimUser admin = new KimUser();
        admin.setUserName("admin");
        // "admin" encrypted
        admin.setUserPassword("$2y$10$038vqDvwT4VTy9mhDy991OIgNcFJv9PcPaBVNKEzhufIE67nRkIiS");
        admin.setUserEmail("admin@admin.com");
        admin.setRole(Role.ADMIN);
        admin.setGender("admin");
        admin.setFirstName("admin");
        admin.setLastName("admin");
        kimUserRepository.save(customer);
        kimUserRepository.save(admin);
    }

    /**
     * H2 Reset
     */
    @AfterEach
    void tearDown() {
        kimUserRepository.deleteAll();
    }


    /**
     * Customer Login
     */
    @Test
    void loginCustomerTest() {
        KimUser customer = kimUserRepository.findAll().stream()
                .filter(u -> u.getUserName().equals("customer"))
                .findFirst().get();
        final String userName = customer.getUserName();
        final String password = "customer";
        String token = assertDoesNotThrow(() -> authenticationService.login(userName, password));
        assertNotNull(token);
        assertEquals(2, StringUtils.countOccurrencesOf(token, "."));
    }

    /**
     * Admin Login
     */
    @Test
    void loginAdminTest() {
        KimUser admin = kimUserRepository.findAll().stream()
                .filter(u -> u.getUserName().equals("admin"))
                .findFirst().get();
        final String userName = admin.getUserName();
        final String password = "admin";
        String token = assertDoesNotThrow(() -> authenticationService.login(userName, password));
        assertNotNull(token);
        assertEquals(2, StringUtils.countOccurrencesOf(token, "."));
    }
}
