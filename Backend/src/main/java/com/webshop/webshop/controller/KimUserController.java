package com.webshop.webshop.controller;

import com.webshop.webshop.DTO.KimUserDTO;
import com.webshop.webshop.model.KimUser;
import com.webshop.webshop.service.KimUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class KimUserController {

    private final KimUserService kimUserService;

    @PostMapping
    public ResponseEntity<KimUserDTO> createUser(@RequestBody KimUserDTO kimUserDTO) {
        try {
            return new ResponseEntity<KimUserDTO>(kimUserService.save(kimUserDTO), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<KimUserDTO>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/get/{id}")
    public ResponseEntity<KimUserDTO> getUser(@PathVariable Long id) {
        return new ResponseEntity<>(kimUserService.findById(id).convertToDto(), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<List<KimUserDTO>> getAllUser() {
        return new ResponseEntity<>(kimUserService.getAllUser()
                .stream()
                .map(KimUser::convertToDto)
                .toList(),
                HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{itemId}")
    public ResponseEntity<KimUserDTO> updateUserById(@RequestBody KimUserDTO kimUserDTO, @PathVariable Long itemId) {
        KimUserDTO updatedUser = kimUserService.update(itemId, kimUserDTO);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public void deleteUser(@PathVariable Long id) {
        kimUserService.deleteById(id);
    }

    /*
    example: JSON input for creating a new user
        {
                "userName":"Mais",
                "userPassword":"1234",
                "eMail":"abd@def.com",
                "userRole":"ADMIN",
                "gender":"m",
                "firstName":"Michael",
                "lastName":"Mayr",
                "address":{
                    "street":"Hauptstrasse",
                    "number":"25",
                    "zip":1200,
                    "city":"Wien",
                    "country":"Austria"
            }
        }
    */

}
