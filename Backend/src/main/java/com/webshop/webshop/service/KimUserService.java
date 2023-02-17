package com.webshop.webshop.service;

import com.webshop.webshop.DTO.KimUserDTO;
import com.webshop.webshop.model.KimUser;
import com.webshop.webshop.repository.AddressRepository;
import com.webshop.webshop.repository.KimUserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class KimUserService {
    private KimUserRepository kimUserRepository;
    private AddressRepository addressRepository;

    public KimUserService(KimUserRepository kimUserRepository, AddressRepository addressRepository) {
        this.kimUserRepository = kimUserRepository;
        this.addressRepository = addressRepository;
    }

    public KimUserDTO save(KimUser kimUser) {
        KimUser result = kimUserRepository.save(kimUser);
        return new KimUserDTO(result.getKimUserId(), result.getUserName(), result.getUserPassword(), result.geteMail(), result.getGender(), result.getFirstName(), result.getLastName(), result.getAddress());
    }

    public KimUserDTO findById(Long id) {
        Optional<KimUser> data = kimUserRepository.findById(id);
        return createKimUserRequestDTO(data);
    }

    public void deleteById(Long id) {
        kimUserRepository.deleteById(id);
    }


    private KimUserDTO createKimUserRequestDTO(Optional<KimUser> optionalKimUser) {
        if (optionalKimUser.isPresent()) {
            KimUser data = optionalKimUser.get();
            return new KimUserDTO(data.getKimUserId(), data.getUserName(), data.getUserPassword(), data.geteMail(), data.getGender(), data.getFirstName(), data.getLastName(), data.getAddress());
        }
        return null;
    }


}
