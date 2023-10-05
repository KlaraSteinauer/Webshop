package com.webshop.webshop.controller;

import com.webshop.webshop.DTO.AddressDTO;
import com.webshop.webshop.config.model.Address;
import com.webshop.webshop.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/address")
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;

    @PostMapping("/add")
    public ResponseEntity<AddressDTO> createAddress(@RequestBody Address address) {
        return new ResponseEntity<>(addressService.save(address), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AddressDTO> findById(@PathVariable Long id) {
        return new ResponseEntity<>(addressService.findById(id).convertToDto(), HttpStatus.OK);
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<AddressDTO>> getAllAddress() {
        return new ResponseEntity<>(addressService.getAllAddress()
                .stream()
                .map(Address::convertToDto)
                .toList(),
                HttpStatus.OK);
    }

}
