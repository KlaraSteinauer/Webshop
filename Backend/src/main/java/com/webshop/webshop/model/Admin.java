package com.webshop.webshop.model;

import com.webshop.webshop.enums.Role;
import jakarta.persistence.Entity;

@Entity(name = "admin")
public class Admin extends Operator {

    public Admin(Long id, String userName, String userPassword, Role userRole) {
        super(id, userName, userPassword, userRole);
    }

    public Admin() {

    }

}
