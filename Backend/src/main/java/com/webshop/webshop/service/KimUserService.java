package com.webshop.webshop.service;

import com.webshop.webshop.DTO.KimUserDTO;
import com.webshop.webshop.config.model.KimUser;
import com.webshop.webshop.config.model.Product;
import com.webshop.webshop.repository.KimUserRepository;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class KimUserService {

    @Autowired
    KimUserRepository kimUserRepository;


    public KimUser save(KimUserDTO kimUserDTO) {
        try {
            return kimUserRepository.save(kimUserDTO.convertToKimUser());
        } catch (NullPointerException e) {
            throw new IllegalArgumentException("Invalid fields for user!");
        }
    }

    public KimUser findById(Long id) {
        var kimUser = kimUserRepository.findById(id);
        if (kimUser.isEmpty()) {
            throw new ObjectNotFoundException(kimUser, "User not found.");
        }
        return kimUser.get();
    }

    public void deleteById(Long id) {
        try {
            kimUserRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ObjectNotFoundException(Product.class, "User with id: " + id + "not found!");
        }
    }
}
