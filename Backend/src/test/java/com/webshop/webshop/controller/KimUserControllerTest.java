package com.webshop.webshop.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.webshop.webshop.DTO.KimUserDTO;
import com.webshop.webshop.enums.Role;
import com.webshop.webshop.model.KimUser;
import com.webshop.webshop.repository.KimUserRepository;
import com.webshop.webshop.service.TokenService;
import org.hamcrest.Matchers;
import org.jose4j.jwt.GeneralJwtException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest
@ActiveProfiles("test")
@AutoConfigureDataJpa
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ExtendWith(SpringExtension.class)
@ComponentScan("com.webshop.webshop.controller")
@ComponentScan("com.webshop.webshop.service")
@ComponentScan("com.webshop.webshop.repository")
@ComponentScan("com.webshop.webshop.config")
public class KimUserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private KimUserRepository kimUserRepository;

    private String token = "";

    /**
     * H2 Setup
     */
    @BeforeEach
    void setup() throws GeneralJwtException {
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
        token = tokenService.generateToken(admin);
    }

    /**
     * H2 Reset
     */
    @AfterEach
    void tearDown() {
        kimUserRepository.deleteAll();
    }

    @Test
    void createKimUserTest() throws Exception {
        KimUserDTO userToSave = new KimUserDTO();
        userToSave.setUserName("user");
        // "password" encrypted
        userToSave.setUserPassword("$2y$10$CRsUFq1glsvT9d/JdRKjN./8jRiFfCs4otsxIhfh3Z9Z7Ud2qqCue");
        userToSave.setUserEmail("user@email.com");
        userToSave.setRole(Role.CUSTOMER.name());
        userToSave.setGender("male");
        userToSave.setFirstName("userFirst");
        userToSave.setLastName("userLast");
        mockMvc.perform(MockMvcRequestBuilders.post("/users/registration")
                .content(mapper.writeValueAsString(userToSave))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.isActive").isNotEmpty());
    }

    @Test
    void findUserByIdTest() throws Exception {
        final KimUser customer = kimUserRepository.findAll().stream()
                .filter(u -> u.getUserName().equals("customer"))
                .findFirst()
                .get();
        final Long customerId = customer.getId();
        final Long wrongId = 123456L;

        mockMvc.perform(MockMvcRequestBuilders.get("/users/{id}", wrongId)
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isNotFound());

        mockMvc.perform(MockMvcRequestBuilders.get("/users/{id}", customerId)
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.userId").isNotEmpty());
    }

    @Test
    void findYourselfTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/users/me")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.userId").isNotEmpty());
    }

    @Test
    void findAllTest() throws Exception {mockMvc.perform(MockMvcRequestBuilders.get("/users")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", Matchers.is(2)))
                .andExpect(jsonPath("$.[*]").isNotEmpty());

    }

    @Test
    void updateUserByIdTest() throws Exception {
        final KimUser customer = kimUserRepository.findAll().stream()
                .filter(u -> u.getUserName().equals("customer"))
                .findFirst()
                .get();
        final Long customerId = customer.getId();
        final Long wrongId = 123456L;
        KimUserDTO userToSave = new KimUserDTO();
        userToSave.setUserName("user");
        // "password" encrypted
        userToSave.setUserPassword("$2y$10$CRsUFq1glsvT9d/JdRKjN./8jRiFfCs4otsxIhfh3Z9Z7Ud2qqCue");
        userToSave.setUserEmail("user@email.com");
        userToSave.setRole(Role.CUSTOMER.name());
        userToSave.setGender("male");
        userToSave.setFirstName("userFirst");
        userToSave.setLastName("userLast");
        final KimUserDTO emptyUser = new KimUserDTO();
        mockMvc.perform(MockMvcRequestBuilders.put("/users/{id}", wrongId)
                        .header("Authorization", "Bearer " + token)
                        .content(mapper.writeValueAsString(userToSave))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        mockMvc.perform(MockMvcRequestBuilders.put("/users/{id}", customerId)
                        .header("Authorization", "Bearer " + token)
                        .content(mapper.writeValueAsString(userToSave))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.userId").isNotEmpty());
    }

    @Test
    void deleteUserTest() throws Exception {
        final KimUser customer = kimUserRepository.findAll().stream()
                .filter(u -> u.getUserName().equals("customer"))
                .findFirst()
                .get();
        final Long customerId = customer.getId();
        final Long wrongId = 123456L;
        mockMvc.perform(MockMvcRequestBuilders.delete("/users/{id}", wrongId)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isNotFound());

        mockMvc.perform(MockMvcRequestBuilders.delete("/users/{id}", customerId)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }

    @Test
    void userActivationTest() throws Exception {
        final KimUser customer = kimUserRepository.findAll().stream()
                .filter(u -> u.getUserName().equals("customer"))
                .findFirst()
                .get();
        final Long customerId = customer.getId();
        final Long wrongId = 123456L;
        mockMvc.perform(MockMvcRequestBuilders.put("/users/activate/{id}", wrongId)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isNotFound());

        mockMvc.perform(MockMvcRequestBuilders.put("/users/deactivate/{id}", wrongId)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isNotFound());

        // User was already active, activity has not changed -> false
        mockMvc.perform(MockMvcRequestBuilders.put("/users/activate/{id}", customerId)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.is(false)));
        // User is now inactive -> true
        mockMvc.perform(MockMvcRequestBuilders.put("/users/deactivate/{id}", customerId)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.is(true)));
        // User was already inactive -> false
        mockMvc.perform(MockMvcRequestBuilders.put("/users/deactivate/{id}", customerId)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.is(false)));
        // User ist now active -> true
        mockMvc.perform(MockMvcRequestBuilders.put("/users/activate/{id}", customerId)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.is(true)));
    }
}
