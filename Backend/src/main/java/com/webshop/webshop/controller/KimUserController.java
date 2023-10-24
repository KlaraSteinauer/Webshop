package com.webshop.webshop.controller;

import com.webshop.webshop.DTO.KimUserDTO;
import com.webshop.webshop.config.SecurityConfig;
import com.webshop.webshop.model.KimUser;
import com.webshop.webshop.security.KimUserDetails;
import com.webshop.webshop.service.KimUserService;
import lombok.RequiredArgsConstructor;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class KimUserController {

    @Autowired
    private PasswordEncoder encoder;
    private final KimUserService kimUserService;


    /**
     * Registers a new User.
     *
     * @param kimUserDTO DTO holding User information
     * @return the created User
     */
    @PostMapping("/registration")
    public ResponseEntity<KimUserDTO> createKimUser(@RequestBody KimUserDTO kimUserDTO) {
        kimUserDTO.setUserPassword(encoder.encode(kimUserDTO.getUserPassword()));
        kimUserDTO.setRole("CUSTOMER");
        return new ResponseEntity<>(kimUserService.save(kimUserDTO).convertToDto(), HttpStatus.CREATED);
    }

    /**
     * Fetches User from the Database.
     *
     * @param id user id
     * @return KimUser
     */
    @GetMapping("/{id}")
    public ResponseEntity<KimUserDTO> findUserById(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(kimUserService.findById(id).convertToDto(), HttpStatus.OK);
        } catch (ObjectNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Fetches User from the Database using the JWT token.
     *
     * @return KimUser
     */
    @GetMapping("/me")
    public ResponseEntity<KimUserDTO> findYourself(@AuthenticationPrincipal Optional<KimUserDetails> user) {
        try {
            return new ResponseEntity<>(kimUserService.findById(user.get().getUserId()).convertToDto(), HttpStatus.OK);
        } catch (ObjectNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Fetches all Users from the Database.
     *
     * @return List of all Users.
     */
    @GetMapping
    public ResponseEntity<List<KimUserDTO>> findAll() {
        List<KimUser> allUsers = kimUserService.findAll();
        return new ResponseEntity<>(allUsers.stream()
                .map(KimUser::convertToDto)
                .collect(Collectors.toList()),
                HttpStatus.OK);
    }

    /**
     * Updates a User.
     *
     * @param kimUserDTO DTO holding information of updated User
     * @param id id of user to update
     * @return KimUser
     */
    @PutMapping("/{id}")
    public ResponseEntity<KimUserDTO> updateUserById(@RequestBody KimUserDTO kimUserDTO, @PathVariable Long id) {
        try {
            if (kimUserDTO.getUserPassword() == null) {
            return new ResponseEntity<>(kimUserDTO, HttpStatus.BAD_REQUEST);
        }
            kimUserDTO.setUserPassword(encoder.encode(kimUserDTO.getUserPassword()));
            KimUserDTO updatedUser = kimUserService.update(id, kimUserDTO);
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        } catch (ObjectNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (DataAccessException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    /**
     * Removes a User from the Database.
     *
     * @param id user id
     * @return HttpStatus code
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable Long id) {
        try {
            kimUserService.deleteById(id);
            return new ResponseEntity(HttpStatus.OK);
        } catch (ObjectNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Activates a User.
     *
     * @param id user id
     * @return true if activity has been changed
     *          / false otherwise
     */
    @PutMapping("/activate/{id}")
    public ResponseEntity<Boolean> activateUser(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(kimUserService.activateUser(id), HttpStatus.OK);
        } catch (ObjectNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Deactivates a User.
     *
     * @param id userId
     * @return true if activity has been changed
     *          / false otherwise
     */
    @PutMapping("/deactivate/{id}")
    public ResponseEntity<Boolean> deactivateUser(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(kimUserService.deactivateUser(id), HttpStatus.OK);
        } catch (ObjectNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
