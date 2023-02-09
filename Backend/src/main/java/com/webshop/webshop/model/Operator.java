package com.webshop.webshop.model;

import com.webshop.webshop.enums.Role;
import jakarta.persistence.*;

@Entity(name = "user")
public class Operator extends Person {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Column(name = "username")
    private String userName;
    @Column(name = "password")
    private String userPassword;
    @Column(name = "userRole")
    private Role userRole;

    public Operator(Long id, String userName, String userPassword, Role userRole) {
        this.id = id;
        this.userName = userName;
        this.userPassword = userPassword;
        this.userRole = userRole;
    }

    public Operator(Long id, String title, String firstname, String lastname, String street, int zip, String city, String country, Long id1) {
        super(id, title, firstname, lastname, street, zip, city, country);
        this.id = id1;
    }

    public Operator() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Role getUserRole() {
        return userRole;
    }

    public void setUserRole(Role userRole) {
        this.userRole = userRole;
    }
}
