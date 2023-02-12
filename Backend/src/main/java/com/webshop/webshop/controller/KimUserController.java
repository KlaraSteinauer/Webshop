package com.webshop.webshop.controller;

import com.webshop.webshop.model.KimUser;
import com.webshop.webshop.model.Product;
import com.webshop.webshop.service.KimUserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/user")
public class KimUserController {

    private KimUserService kimUserService;

    private KimUserController(KimUserService kimUserService) {
        this.kimUserService = kimUserService;
    }


/*
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
    // TODO figure out how to add address to JSON
    //      or run createAddress and connect it to user
    @PostMapping // creates a new user (JSON)
    public ResponseEntity<KimUser> createKimUser(@RequestBody KimUser kimUser) {
        kimUser = kimUserService.save(kimUser);
        return ResponseEntity.created(URI.create("http://localhost:8080/user")).body(kimUser);
    }
    /*
    - neuen User mit DTO erstellen
    - user in DB speichern
    - user kann sich einloggen
    - schauen ob user admin ist

    USER
    - user kann produkte zum shoppingcart hinzufügen
    - user kann produkte zum shoppingcart löschen

    ADMIN
    - kann produkte


     */





}
