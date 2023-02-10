package com.webshop.webshop.service;

import com.webshop.webshop.repository.AddressRepository;

public class AddressService {

    private AddressRepository addressRepository;

    private AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }
}
