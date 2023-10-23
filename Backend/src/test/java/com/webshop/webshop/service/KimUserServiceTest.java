package com.webshop.webshop.service;

import com.webshop.webshop.DTO.KimUserDTO;
import com.webshop.webshop.WebshopApplication;
import com.webshop.webshop.enums.Role;
import com.webshop.webshop.model.KimUser;
import com.webshop.webshop.repository.KimUserRepository;
import com.webshop.webshop.security.KimUserDetails;
import org.hibernate.ObjectNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest(classes = WebshopApplication.class)
@ActiveProfiles("test")
public class KimUserServiceTest {

    @Autowired
    private KimUserService kimUserService;

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
     * Saves a new User.
     */
    @Test
    void saveTest() {
        // 2 Users in DB
        assertEquals(2, kimUserRepository.findAll().size());
        KimUserDTO userToSave = new KimUserDTO();
        userToSave.setUserName("user");
        // "password" encrypted
        userToSave.setUserPassword("$2y$10$CRsUFq1glsvT9d/JdRKjN./8jRiFfCs4otsxIhfh3Z9Z7Ud2qqCue");
        userToSave.setUserEmail("user@email.com");
        userToSave.setRole(Role.CUSTOMER.name());
        userToSave.setGender("male");
        userToSave.setFirstName("userFirst");
        userToSave.setLastName("userLast");
        final KimUser savedUser = assertDoesNotThrow(() -> kimUserService.save(userToSave));
        assertAll(
                () -> assertEquals(userToSave.getUserName(), savedUser.getUserName()),
                () -> assertEquals(userToSave.getUserPassword(), savedUser.getUserPassword()),
                () -> assertEquals(userToSave.getUserEmail(), savedUser.getUserEmail()),
                () -> assertEquals(Role.valueOf(userToSave.getRole()), savedUser.getRole()),
                () -> assertEquals(userToSave.getGender(), savedUser.getGender()),
                () -> assertEquals(userToSave.getFirstName(), savedUser.getFirstName()),
                () -> assertEquals(userToSave.getLastName(), savedUser.getLastName())
        );
        // 3 Users in DB
        assertEquals(3, kimUserRepository.findAll().size());
    }

    /**
     * Fetches a User from DB.
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

    /**
     *  Fetches a User from DB using userName which is unique.
     */
    @Test
    void findByUserNameTest() {
        final KimUser customer = kimUserRepository.findAll().stream()
                .filter(u -> u.getUserName().equals("customer"))
                .findFirst()
                .get();
        final String userName = customer.getUserName();
        final String wrongUserName = "wrongUserName";
        assertThrows(ObjectNotFoundException.class, () -> kimUserService.findByUserName(wrongUserName));
        KimUser foundUser = assertDoesNotThrow(() -> kimUserService.findByUserName(userName));
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

    /**
     * Fetches all Users from DB.
     */
    @Test
    void findAllTest() {
        List<KimUser> users = assertDoesNotThrow(() -> kimUserService.findAll());
        assertEquals(2, users.size());
        kimUserRepository.deleteAll();
        assertThrows(ObjectNotFoundException.class,
                () -> kimUserService.findAll());
    }

    /**
     * Updates a User, ignoring ID & Role.
     */
    @Test
    void updateTest() {
        final KimUser customer = kimUserRepository.findAll().stream()
                .filter(u -> u.getUserName().equals("customer"))
                .findFirst()
                .get();
        final Long userId = customer.getId();
        final Long wrongId = 123456L;
        assertEquals(2, kimUserRepository.findAll().size());
        KimUserDTO updateDto = new KimUserDTO();
        updateDto.setUserId(9999L);
        updateDto.setUserName("updatedUser");
        // "password" encrypted
        updateDto.setUserPassword("$2y$10$CRsUFq1glsvT9d/JdRKjN./8jRiFfCs4otsxIhfh3Z9Z7Ud2qqCue");
        updateDto.setUserEmail("updatedUser@updatedUser.com");
        updateDto.setRole(Role.ANONYMOUS.name());
        updateDto.setGender("updatedUser");
        updateDto.setFirstName("updatedFirst");
        updateDto.setLastName("updatedLast");

        assertThrows(ObjectNotFoundException.class,
                () -> kimUserService.update(wrongId, updateDto));
        // no changes to DB
        assertEquals(2, kimUserRepository.findAll().size());
        KimUserDTO updatedUser = assertDoesNotThrow(() -> kimUserService.update(userId, updateDto));
        assertAll(
                // ID unchanged
                () -> assertEquals(customer.getId(), updatedUser.getUserId()),
                () -> assertEquals(updateDto.getUserName(), updatedUser.getUserName()),
                () -> assertEquals(updateDto.getUserPassword(), updatedUser.getUserPassword()),
                () -> assertEquals(updateDto.getUserEmail(), updatedUser.getUserEmail()),
                // Role unchanged
                () -> assertEquals(customer.getRole().name(), updatedUser.getRole()),
                () -> assertEquals(updateDto.getGender(), updatedUser.getGender()),
                () -> assertEquals(updateDto.getFirstName(), updatedUser.getFirstName()),
                () -> assertEquals(updateDto.getLastName(), updatedUser.getLastName())
        );
        // no new User was created
        assertEquals(2, kimUserRepository.findAll().size());
    }

    /**
     * Deletes a User.
     */
    @Test
    void deleteByIdTest() {
        final KimUser customer = kimUserRepository.findAll().stream()
                .filter(u -> u.getUserName().equals("customer"))
                .findFirst()
                .get();
        final Long idToDelete = customer.getId();
        final Long wrongId = 123456L;
        // 2 Users in DB
        assertEquals(2, kimUserRepository.findAll().size());
        assertThrows(ObjectNotFoundException.class,
                () -> kimUserService.deleteById(wrongId));
        // make sure no user was deleted
        assertEquals(2, kimUserRepository.findAll().size());
        assertDoesNotThrow(() -> kimUserService.deleteById(idToDelete));
        // 1 User in DB
        assertEquals(1, kimUserRepository.findAll().size());
    }

    /**
     * Loads UserDetails from userName.
     */
    @Test
    void loadUserByUsernameTest() {
        final KimUser customer = kimUserRepository.findAll().stream()
                .filter(u -> u.getUserName().equals("customer"))
                .findFirst()
                .get();
        final String userName = customer.getUserName();
        final String wrongUserName = "wrongUserName";

        assertThrows(UsernameNotFoundException.class,
                () -> kimUserService.loadUserByUsername(wrongUserName));

        UserDetails details = assertDoesNotThrow(() -> kimUserService.loadUserByUsername(userName));
        // KimuserDetails child class of UserDetails
        KimUserDetails kimUserDetails = assertDoesNotThrow(() -> (KimUserDetails) details);
        assertAll(
                () -> assertEquals(customer.getId(), kimUserDetails.getUserId()),
                () -> assertEquals(customer.getUserName(), kimUserDetails.getUserName()),
                () -> assertEquals(customer.getUserPassword(), kimUserDetails.getPassword()),
                () -> assertEquals(customer.getRole(), kimUserDetails.getUserRole())
        );
    }

    @Test
    void userActivationTest() {
        KimUser customer = kimUserRepository.findAll().stream()
                .filter(u -> u.getUserName().equals("customer"))
                .findFirst()
                .get();
        final Long userId = customer.getId();
        assertTrue(customer.isActive());
        boolean activityHasChanged = assertDoesNotThrow(() -> kimUserService.deactivateUser(userId));
        assertTrue(activityHasChanged);
        customer = kimUserRepository.findAll().stream()
                .filter(u -> u.getUserName().equals("customer"))
                .findFirst()
                .get();
        assertFalse(customer.isActive());
        activityHasChanged = assertDoesNotThrow(() -> kimUserService.deactivateUser(userId));
        assertFalse(activityHasChanged);
        customer = kimUserRepository.findAll().stream()
                .filter(u -> u.getUserName().equals("customer"))
                .findFirst()
                .get();
        assertFalse(customer.isActive());
        activityHasChanged = assertDoesNotThrow(() -> kimUserService.activateUser(userId));
        assertTrue(activityHasChanged);
        customer = kimUserRepository.findAll().stream()
                .filter(u -> u.getUserName().equals("customer"))
                .findFirst()
                .get();
        assertTrue(customer.isActive());
        activityHasChanged = assertDoesNotThrow(() -> kimUserService.activateUser(userId));
        assertFalse(activityHasChanged);
        customer = kimUserRepository.findAll().stream()
                .filter(u -> u.getUserName().equals("customer"))
                .findFirst()
                .get();
        assertTrue(customer.isActive());
    }


}
