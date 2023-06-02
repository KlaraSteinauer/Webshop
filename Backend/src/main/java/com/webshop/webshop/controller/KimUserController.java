package com.webshop.webshop.controller;

import com.webshop.webshop.DTO.KimUserDTO;
import com.webshop.webshop.model.KimUser;
import com.webshop.webshop.service.KimUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public record KimUserController(KimUserService kimUserService) {


    @PostMapping("/add") //DTO übergeben
    public ResponseEntity<KimUserDTO> createKimUser(@RequestBody KimUser kimUser) {
        return new ResponseEntity<>(kimUserService.save(kimUser).convertToDto(), HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<KimUserDTO> getUser(@PathVariable Long id) {
        return new ResponseEntity<>(kimUserService.findById(id).convertToDto(), HttpStatus.OK);
    }

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
