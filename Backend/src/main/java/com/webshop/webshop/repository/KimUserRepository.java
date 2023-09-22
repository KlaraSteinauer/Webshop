package com.webshop.webshop.repository;

import com.webshop.webshop.model.KimUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface KimUserRepository extends JpaRepository<KimUser, Long> {

    @Query("SELECT k FROM KimUser k " +
            "WHERE k.userName = :name " +
            "AND k.userPassword = :password")
    Optional<KimUser> findByUserNameAndUserPassword(@Param("name") String userName, @Param("password") String userPassword);

}
