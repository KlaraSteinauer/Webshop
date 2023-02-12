package com.webshop.webshop.controller;

import com.webshop.webshop.model.Address;
import com.webshop.webshop.service.AddressService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/address")
public class AddressController {

    private AddressService addressService;

    private AddressController(AddressService addressService) {
        this.addressService = addressService;
    }
@PostMapping // creates a new address (JSON)
    public ResponseEntity<Address> createAddress(@RequestBody Address address){
        address=addressService.save(address);
        return ResponseEntity.created(URI.create("http://localhost:8080/address")).body(address);
}




    /*
    - neue addresse erstellen mit DTO
    - addresse in DB speichern
     */
}
