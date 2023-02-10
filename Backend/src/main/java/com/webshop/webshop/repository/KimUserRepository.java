package com.webshop.webshop.repository;

import com.webshop.webshop.model.KimUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KimUserRepository extends JpaRepository <KimUser, Long> {

}
