package com.webshop.webshop.controller;

import com.webshop.webshop.DTO.AddressDTO;
import com.webshop.webshop.model.Address;
import com.webshop.webshop.repository.AddressRepository;
import com.webshop.webshop.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/address")
public class AddressController {

    @Autowired
    private AddressService addressService;
    private final AddressRepository addressRepository;

    private AddressController(AddressService addressService,
                              AddressRepository addressRepository) {
        this.addressService = addressService;
        this.addressRepository = addressRepository;
    }

    @PostMapping("/add")
    public ResponseEntity<AddressDTO> createAddress(@RequestBody Address address) {
        return new ResponseEntity<>(addressService.save(address), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AddressDTO> findById(@PathVariable Long id) {
        return new ResponseEntity<>(addressService.findById(id), HttpStatus.OK);
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<AddressDTO>> getAllAddress() {
        return new ResponseEntity<>(addressService.getAllAddress(), HttpStatus.OK);
    }

}
