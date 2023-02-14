package com.webshop.webshop.service;

import com.webshop.webshop.model.KimUser;
import com.webshop.webshop.model.Product;
import com.webshop.webshop.repository.AddressRepository;
import com.webshop.webshop.repository.KimUserRepository;
import com.webshop.webshop.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class KimUserService {
    private KimUserRepository kimUserRepository;
    private AddressRepository addressRepository;

    public KimUserService(KimUserRepository kimUserRepository, AddressRepository addressRepository) {
        this.kimUserRepository = kimUserRepository;
        this.addressRepository = addressRepository;
    }
    public KimUser save(KimUser kimUser) {
        //hier muss/sollte man nochmals validieren
        String name = kimUser.getUserName();
        if (name == null || name.isBlank()) {
            throw new EntityNotFoundException();
        }

        return kimUserRepository.save(kimUser);
    }

}
