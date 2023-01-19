package com.webshop.webshop.repository;

import com.webshop.webshop.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

    //TODO kommt hier die Methode isAdmin hinein?


}
