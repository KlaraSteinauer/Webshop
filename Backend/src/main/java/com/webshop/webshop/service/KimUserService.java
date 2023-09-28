package com.webshop.webshop.service;

import com.webshop.webshop.DTO.KimUserDTO;
import com.webshop.webshop.model.KimUser;
import com.webshop.webshop.repository.KimUserRepository;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KimUserService {

    @Autowired
    KimUserRepository kimUserRepository;

    public KimUser save(KimUserDTO kimUserDTO) {
        return kimUserRepository.save(kimUserDTO.convertToKimUser());
    }

    public KimUser findById(Long id) {
        var kimUser = kimUserRepository.findById(id);
        if (kimUser.isEmpty()) {
            throw new ObjectNotFoundException(kimUser, "User not found.");
        }
        return kimUser.get();
    }

    public void deleteById(Long id) {
        kimUserRepository.deleteById(id);
    }
}
