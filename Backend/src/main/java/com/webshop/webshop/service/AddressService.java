package com.webshop.webshop.service;

import com.webshop.webshop.DTO.AddressDTO;
import com.webshop.webshop.model.Address;
import com.webshop.webshop.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public AddressDTO findById(Long id) {
        Optional<Address> optionalAddress = addressRepository.findById(id);
        if (optionalAddress.isPresent()) {
            return toDto(optionalAddress.get());
        }
        return null;
    }

    public List<AddressDTO> getAllAddress() {
        List<Address> addresses = addressRepository.findAll();
        return toDtos(addresses);
    }

    private AddressDTO toDto(Address data) {
        return new AddressDTO(data.getAddressId(), data.getStreet(), data.getNumber(), data.getZip(), data.getCity(), data.getCountry());
    }

    private List<AddressDTO> toDtos(List<Address> addresses) {
        return addresses.stream()
                .map(this::toDto)
                .toList();
    }
}
