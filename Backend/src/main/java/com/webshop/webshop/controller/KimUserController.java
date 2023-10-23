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


    @PostMapping("/registration")
    public ResponseEntity<KimUserDTO> createKimUser(@RequestBody KimUserDTO kimUserDTO) {
        kimUserDTO.setUserPassword(encoder.encode(kimUserDTO.getUserPassword()));
        kimUserDTO.setRole("CUSTOMER");
        return new ResponseEntity<>(kimUserService.save(kimUserDTO).convertToDto(), HttpStatus.CREATED);
    }


    @GetMapping("/{id}")
    public ResponseEntity<KimUserDTO> findUserById(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(kimUserService.findById(id).convertToDto(), HttpStatus.OK);
        } catch (ObjectNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @GetMapping("/me")
    public ResponseEntity<KimUserDTO> findYourself(@AuthenticationPrincipal Optional<KimUserDetails> user) {
        try {
            return new ResponseEntity<>(kimUserService.findById(user.get().getUserId()).convertToDto(), HttpStatus.OK);
        } catch (ObjectNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @GetMapping
    public ResponseEntity<List<KimUserDTO>> findAll() {
        List<KimUser> allUsers = kimUserService.findAll();
        return new ResponseEntity<>(allUsers.stream()
                .map(KimUser::convertToDto)
                .collect(Collectors.toList()),
                HttpStatus.OK);
    }


    @PutMapping("/{id}")
    public ResponseEntity<KimUserDTO> updateUserById(@RequestBody KimUserDTO kimUserDTO, @PathVariable Long id) {
        try {
            KimUserDTO updatedUser = kimUserService.update(id, kimUserDTO);
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        } catch (ObjectNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (DataAccessException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }


    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable Long id) {
        try {
            kimUserService.deleteById(id);
            return new ResponseEntity(HttpStatus.OK);
        } catch (ObjectNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/activate/{id}")
    public ResponseEntity<Boolean> activateUser(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(kimUserService.activateUser(id), HttpStatus.OK);
        } catch (ObjectNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/deactivate/{id}")
    public ResponseEntity<Boolean> deactivateUser(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(kimUserService.deactivateUser(id), HttpStatus.OK);
        } catch (ObjectNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
