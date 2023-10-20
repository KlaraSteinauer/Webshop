package com.webshop.webshop.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.webshop.webshop.DTO.LoginDTO;
import com.webshop.webshop.enums.Role;
import com.webshop.webshop.model.KimUser;
import com.webshop.webshop.repository.KimUserRepository;
import org.junit.jupiter.api.AfterEach;
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
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.StringUtils;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
    void loginCustomerTest() throws Exception {
        KimUser customer = kimUserRepository.findAll().stream()
                .filter(u -> u.getUserName().equals("customer"))
                .findFirst().get();        final String userName = customer.getUserName();
        final String password = "customer";
        final LoginDTO request = new LoginDTO(userName, password);

        String response = mockMvc.perform(MockMvcRequestBuilders
                        .post("/login")
                        .content(mapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andReturn().getResponse().getContentAsString();
        assertEquals("Bearer ", response.substring(0, 7));
        assertEquals(2, StringUtils.countOccurrencesOf(response.substring(7), "."));

    }

    /**
     * Admin Login
     */
    @Test
    void loginAdminTest() throws Exception {
        KimUser admin = kimUserRepository.findAll().stream()
                .filter(u -> u.getUserName().equals("admin"))
                .findFirst().get();
        final String userName = admin.getUserName();
        final String password = "admin";
        final LoginDTO request = new LoginDTO(userName, password);
        String response = mockMvc.perform(MockMvcRequestBuilders
                        .post("/login")
                        .content(mapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andReturn().getResponse().getContentAsString();
        assertEquals("Bearer ", response.substring(0, 7));
        assertEquals(2, StringUtils.countOccurrencesOf(response.substring(7), "."));

    }
}
