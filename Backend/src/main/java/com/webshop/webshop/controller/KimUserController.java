package com.webshop.webshop.controller;

import com.webshop.webshop.model.KimUser;
import com.webshop.webshop.requestDTO.KimUserDTO;
import com.webshop.webshop.service.KimUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class KimUserController {

    @Autowired
    private KimUserService kimUserService;

    private KimUserController(KimUserService kimUserService) {
        this.kimUserService = kimUserService;
    }

    @PostMapping("/add")
    public KimUserDTO createKimUser(@RequestBody KimUser kimUser) {
        return kimUserService.save(kimUser);
    }

    @GetMapping("/get/{id}")
    public KimUserDTO getUser(@PathVariable Long id) {
        return kimUserService.findById(id);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteUser(@PathVariable Long id) {
        kimUserService.deleteById(id);
    }

    /*
    // TODO figure out how to add address to JSON
    // or run createAddress and connect it to user
        {
                "userName":"Mais",
                "userPassword":"1234",
                "eMail":"abd@def.com",
                "userRole":"admin",
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
