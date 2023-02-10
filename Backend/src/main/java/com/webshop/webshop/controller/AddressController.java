package com.webshop.webshop.controller;

import com.webshop.webshop.service.AddressService;

public class AddressController {

    private AddressService addressService;

    private AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    /*
    - neue addresse erstellen mit DTO
    - addresse in DB speichern
     */
}
