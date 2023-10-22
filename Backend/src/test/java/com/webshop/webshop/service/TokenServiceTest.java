package com.webshop.webshop.service;

import com.webshop.webshop.WebshopApplication;
import com.webshop.webshop.enums.Role;
import com.webshop.webshop.model.KimUser;
import com.webshop.webshop.repository.KimUserRepository;
import com.webshop.webshop.security.KimUserDetails;
import org.jose4j.jwt.GeneralJwtException;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.StringUtils;

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
     * Generates token for User.
     *
     * @throws GeneralJwtException if token is invalid.
     */
    @Test
    void generateTokenTest() throws GeneralJwtException {
        final KimUser customer = kimUserRepository.findAll().stream()
                .filter(u -> u.getUserName().equals("customer"))
                .findFirst()
                .get();
        final String customerToken = assertDoesNotThrow(() -> tokenService.generateToken(customer));
        assertNotNull(customerToken);
        assertEquals(2, StringUtils.countOccurrencesOf(customerToken, "."));
        final KimUser admin = kimUserRepository.findAll().stream()
                .filter(u -> u.getUserName().equals("admin"))
                .findFirst()
                .get();
        final String adminToken = assertDoesNotThrow(() -> tokenService.generateToken(admin));
        assertNotNull(adminToken);
        assertEquals(2, StringUtils.countOccurrencesOf(adminToken, "."));
        KimUser wrongCustomer = customer;
        wrongCustomer.setUserName(null);
        assertThrows(IllegalArgumentException.class,
                () -> tokenService.generateToken(wrongCustomer));
    }

    /**
     * Generates token for User(from UserDetails).
     */
    @Test
    void generateTokenFromUserDetailsTest() {
        final KimUser customer = kimUserRepository.findAll().stream()
                .filter(u -> u.getUserName().equals("customer"))
                .findFirst()
                .get();
        final KimUser admin = kimUserRepository.findAll().stream()
                .filter(u -> u.getUserName().equals("admin"))
                .findFirst()
                .get();
        final KimUserDetails customerDetails = new KimUserDetails(
                customer.getId(), customer.getUserName(), "customer", customer.getRole());
        final KimUserDetails adminDetails = new KimUserDetails(
                admin.getId(), admin.getUserName(), "admin", admin.getRole());
        String customerToken = assertDoesNotThrow(() -> tokenService.generateTokenFromUserDetails(customerDetails));
        String adminToken = assertDoesNotThrow(() -> tokenService.generateTokenFromUserDetails(adminDetails));
        assertNotNull(customerToken);
        Assertions.assertEquals(2, StringUtils.countOccurrencesOf(customerToken, "."));
        assertNotNull(adminToken);
        Assertions.assertEquals(2, StringUtils.countOccurrencesOf(adminToken, "."));
    }

    /**
     * Parses tokens.
     */
    @Test
    void parseTokenTest() {
        final KimUser customer = kimUserRepository.findAll().stream()
                .filter(u -> u.getUserName().equals("customer"))
                .findFirst()
                .get();
        final KimUser admin = kimUserRepository.findAll().stream()
                .filter(u -> u.getUserName().equals("admin"))
                .findFirst()
                .get();
        final String customerToken = assertDoesNotThrow(() -> tokenService.generateToken(customer));
        Optional<KimUserDetails> optionalCustomerDetails = assertDoesNotThrow(() -> tokenService.parseToken(customerToken));
        KimUserDetails customerDetails = assertDoesNotThrow(() -> optionalCustomerDetails.get());
        assertAll(
                () -> assertEquals(customer.getId(), customerDetails.getUserId()),
                () -> assertEquals(customer.getUserName(), customerDetails.getUserName()),
                () -> assertEquals(customer.getRole(), customerDetails.getUserRole())
        );
        final String adminToken = assertDoesNotThrow(() -> tokenService.generateToken(admin));
        Optional<KimUserDetails> optionalAdminDetails = assertDoesNotThrow(() -> tokenService.parseToken(adminToken));
        KimUserDetails adminDetails = assertDoesNotThrow(() -> optionalAdminDetails.get());
        assertAll(
                () -> assertEquals(admin.getId(), adminDetails.getUserId()),
                () -> assertEquals(admin.getUserName(), adminDetails.getUserName()),
                () -> assertEquals(admin.getRole(), adminDetails.getUserRole())
        );
        final String wrongToken = "hb2le)bve.ieub/31ge.ergjiabett6k";
        assertThrows(Exception.class,
                () -> tokenService.parseToken(wrongToken));
    }

    /**
     * Cheks if User has Role ADMIN.
     */
    @Test
    void isAdminTest() {
        final KimUser customer = kimUserRepository.findAll().stream()
                .filter(u -> u.getUserName().equals("customer"))
                .findFirst()
                .get();
        final String customerToken = assertDoesNotThrow(() -> tokenService.generateToken(customer));
        assertFalse(tokenService.isAdmin(customerToken));

        final KimUser admin = kimUserRepository.findAll().stream()
                .filter(u -> u.getUserName().equals("admin"))
                .findFirst()
                .get();
        final String adminToken = assertDoesNotThrow(() -> tokenService.generateToken(admin));
        assertTrue(tokenService.isAdmin(adminToken));
    }
}
