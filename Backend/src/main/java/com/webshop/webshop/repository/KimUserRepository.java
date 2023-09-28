package com.webshop.webshop.repository;

import com.webshop.webshop.model.KimUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface KimUserRepository extends JpaRepository<KimUser, Long> {



Optional<KimUser> findByUserNameAndUserPassword(String userName, String userPassword);

}
