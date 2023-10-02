package com.webshop.webshop.service;

import com.webshop.webshop.DTO.AddressDTO;
import com.webshop.webshop.model.Address;
import com.webshop.webshop.repository.AddressRepository;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressService {

    @Autowired
    AddressRepository addressRepository;

    public AddressDTO save(Address address) {
        Address data = addressRepository.save(address);
        return new AddressDTO(
                data.getStreet(),
                data.getNumber(),
                data.getZip(),
                data.getCity(),
                data.getCountry());
    }

    public Address findById(Long id) throws ObjectNotFoundException {
        var address = addressRepository.findById(id);
        if (address.isEmpty()) {
            throw new ObjectNotFoundException(address, "Address not found.");
        }
        return address.get();
    }

    public List<Address> getAllAddress() {
        return addressRepository.findAll();
    }
}
