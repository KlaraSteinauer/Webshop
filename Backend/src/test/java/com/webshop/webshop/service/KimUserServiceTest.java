package com.webshop.webshop.service;

import com.webshop.webshop.WebshopApplication;
import com.webshop.webshop.enums.Role;
import com.webshop.webshop.model.KimUser;
import com.webshop.webshop.repository.KimUserRepository;
import org.hibernate.ObjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest(classes = WebshopApplication.class)
@ActiveProfiles("test")
public class KimUserServiceTest {

    @Autowired
    private KimUserService kimUserService;

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

/*    @Disabled
    @Test
    void saveTest() {
        assertEquals(3, kimUserRepository.findAll().size());
        KimUserDTO user = new KimUserDTO();
        user.setUserName("user");
        user.setUserPassword("userPassword");
        user.setUserEmail("user@email.com");
        user.setRole(Role.CUSTOMER.name());
        user.setGender("male");
        user.setFirstName("userFirst");
        user.setLastName("userLast");
        final KimUserDTO savedUser = assertDoesNotThrow(() -> kimUserService.save(user));
        assertAll(
                () -> assertEquals(user.getUserName(), savedUser.getUserName()),
                () -> assertEquals(user.getUserPassword(), savedUser.getUserPassword()),
                () -> assertEquals(user.getUserEmail(), savedUser.getUserEmail()),
                () -> assertEquals(Role.valueOf(user.getRole()), savedUser.getRole()),
                () -> assertEquals(user.getGender(), savedUser.getGender()),
                () -> assertEquals(user.getFirstName(), savedUser.getFirstName()),
                () -> assertEquals(user.getLastName(), savedUser.getLastName())
        );
        assertEquals(4, kimUserRepository.findAll().size());
    }
*/

    @Test
    void findByIdTest() {
        final KimUser customer = kimUserRepository.findAll().stream()
                .filter(u -> u.getUserName().equals("customer"))
                .findFirst()
                .get();
        final Long customerId = customer.getId();
        final Long wrongId = 123456L;
        assertThrows(ObjectNotFoundException.class, () -> kimUserService.findById(wrongId));
        KimUser foundUser = assertDoesNotThrow(() -> kimUserService.findById(customerId));
        assertAll(
                () -> assertEquals(customer.getId(), foundUser.getId()),
                () -> assertEquals(customer.getUserName(), foundUser.getUserName()),
                () -> assertEquals(customer.getUserPassword(), foundUser.getUserPassword()),
                () -> assertEquals(customer.getUserEmail(), foundUser.getUserEmail()),
                () -> assertEquals(customer.getRole(), foundUser.getRole()),
                () -> assertEquals(customer.getGender(), foundUser.getGender()),
                () -> assertEquals(customer.getFirstName(), foundUser.getFirstName()),
                () -> assertEquals(customer.getLastName(), foundUser.getLastName())
        );
    }


    @Test
    void deleteByIdTest() {
        final KimUser customer = kimUserRepository.findAll().stream()
                .filter(u -> u.getUserName().equals("customer"))
                .findFirst()
                .get();
        final Long idToDelete = customer.getId();
        final Long wrongId = 123456L;
        assertThrows(ObjectNotFoundException.class,
                () -> kimUserService.deleteById(wrongId));
        // make sure no user was deleted
        assertEquals(3, kimUserRepository.findAll().size());
        assertDoesNotThrow(() -> kimUserService.deleteById(idToDelete));
        // only 2 users remain
        assertEquals(2, kimUserRepository.findAll().size());
    }
}
