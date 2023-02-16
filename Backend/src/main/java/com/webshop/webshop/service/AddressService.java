package com.webshop.webshop.service;

import com.webshop.webshop.model.Address;
import com.webshop.webshop.repository.AddressRepository;
import com.webshop.webshop.requestDTO.AddressDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;

    private AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    public AddressDTO save(Address address) {
        Address data = addressRepository.save(address);
        return new AddressDTO(data.getAddressId(), data.getStreet(), data.getNumber(), data.getZip(), data.getCity(), data.getCountry());
    }
}
