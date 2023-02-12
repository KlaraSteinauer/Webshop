package com.webshop.webshop.service;

import com.webshop.webshop.model.Address;
import com.webshop.webshop.model.KimUser;
import com.webshop.webshop.repository.AddressRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AddressService {

    private AddressRepository addressRepository;

    private AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    public Address save(Address address) {
        //hier muss/sollte man nochmals validieren
        String name = address.getStreet();
        if (name == null || name.isBlank()) {
            throw new EntityNotFoundException();
        }
        return addressRepository.save(address);
    }
}
