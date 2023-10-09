package com.webshop.webshop.service;

import com.webshop.webshop.DTO.KimUserDTO;
import com.webshop.webshop.model.KimUser;
import com.webshop.webshop.repository.KimUserRepository;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KimUserService {

    @Autowired
    KimUserRepository kimUserRepository;

    public KimUserDTO save(KimUserDTO kimUserDTO) {
        KimUser user = kimUserDTO.convertToKimUser();
        KimUser savedUser = kimUserRepository.save(user);
        return savedUser.convertToDto();
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

    public List<KimUser> getAllUser() {
        return kimUserRepository.findAll();
    }

    public KimUserDTO update(Long id, KimUserDTO updateKimUserDTO) throws ObjectNotFoundException {
        KimUser user = findById(id);
        user.setUserName(updateKimUserDTO.getUserName());
        user.setUserPassword(updateKimUserDTO.getUserPassword());
        user.setEMail(updateKimUserDTO.getEMail());
        user.setGender(updateKimUserDTO.getGender());
        user.setFirstName(updateKimUserDTO.getFirstname());
        user.setLastName(updateKimUserDTO.getLastname());
        kimUserRepository.save(user);
        return user.convertToDto();
    }

}
