package com.webshop.webshop.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.webshop.webshop.DTO.LoginDTO;
import com.webshop.webshop.enums.Role;
import com.webshop.webshop.model.KimUser;
import com.webshop.webshop.repository.KimUserRepository;
import org.junit.jupiter.api.BeforeEach;
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

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest
@ActiveProfiles("test")
@AutoConfigureDataJpa
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ExtendWith(SpringExtension.class)
@ComponentScan("com.webshop.webshop.controller")
@ComponentScan("com.webshop.webshop.service")
@ComponentScan("com.webshop.webshop.repository")
@ComponentScan("com.webshop.webshop.config")
public class AuthenticationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private KimUserRepository kimUserRepository;

    @BeforeEach
    void setup() {
        kimUserRepository.deleteAll();
        KimUser customer = new KimUser();
        customer.setUserId(1L);
        customer.setUserName("customer");
        customer.setUserPassword("customerPassword");
        customer.setUserEmail("customer@email.com");
        customer.setRole(Role.CUSTOMER);
        customer.setGender("female");
        customer.setFirstName("customerFirst");
        customer.setLastName("customerLast");
        KimUser admin = new KimUser();
        admin.setUserId(2L);
        admin.setUserName("admin");
        admin.setUserPassword("adminPassword");
        admin.setUserEmail("admin@email.com");
        admin.setRole(Role.ADMIN);
        admin.setGender("male");
        admin.setFirstName("adminFirst");
        admin.setLastName("adminLast");
        KimUser anonymous = new KimUser();
        anonymous.setUserId(3L);
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
    void loginTest() throws Exception {
        final KimUser customer = kimUserRepository.findAll().stream()
                .filter(u -> u.getUserName().equals("customer"))
                .findFirst()
                .get();
        final String userName = customer.getUserName();
        final String password = customer.getUserPassword();
        final LoginDTO responseBody = new LoginDTO(userName, password);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/login")
                        .content(mapper.writeValueAsString(responseBody))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").exists());


    }
}
