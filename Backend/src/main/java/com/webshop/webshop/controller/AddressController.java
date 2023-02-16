package com.webshop.webshop.controller;

import com.webshop.webshop.model.Address;
import com.webshop.webshop.requestDTO.AddressDTO;
import com.webshop.webshop.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/address")
public class AddressController {

    @Autowired
    private AddressService addressService;

    private AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @PostMapping("/add") // creates a new address (JSON)
    public AddressDTO createAddress(@RequestBody Address address) {
        return addressService.save(address);
    }
    
}
