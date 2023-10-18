package com.webshop.webshop.controller;

import com.webshop.webshop.DTO.KimUserDTO;
import com.webshop.webshop.config.SecurityConfig;
import com.webshop.webshop.model.KimUser;
import com.webshop.webshop.service.KimUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class KimUserController {

    @Autowired
    private PasswordEncoder encoder;
    private final KimUserService kimUserService;

    @PostMapping
    public ResponseEntity<KimUserDTO> createKimUser(@RequestBody KimUserDTO kimUserDTO) {
        kimUserDTO.setUserPassword(encoder.encode(kimUserDTO.getUserPassword()));
        return new ResponseEntity<>(kimUserService.save(kimUserDTO).convertToDto(), HttpStatus.CREATED);
    }


    @GetMapping("/{id}")
    public ResponseEntity<KimUserDTO> findUserById(@PathVariable Long id) {
        return new ResponseEntity<>(kimUserService.findById(id).convertToDto(), HttpStatus.OK);
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
        KimUserDTO updatedUser = kimUserService.update(id, kimUserDTO);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        kimUserService.deleteById(id);
    }

}
