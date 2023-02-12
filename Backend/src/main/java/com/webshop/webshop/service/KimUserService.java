package com.webshop.webshop.service;

import com.webshop.webshop.model.KimUser;
import com.webshop.webshop.model.Product;
import com.webshop.webshop.repository.KimUserRepository;
import com.webshop.webshop.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class KimUserService {
    private KimUserRepository kimUserRepository;

    public KimUserService(KimUserRepository kimUserRepository) {
        this.kimUserRepository = kimUserRepository;
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
