package com.webshop.webshop.model;

import jakarta.persistence.*;

@Entity
public class KimUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column
    private String lastName;
    @Column
    private String firstName;
    @Column
    private String userName;
    @Column
    private String userPassword;
    @Column
    boolean isAdmin;

    public KimUser(Long id, String lastName, String firstName, String userName, String userPassword, boolean isAdmin) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.userName = userName;
        this.userPassword = userPassword;
        this.isAdmin = isAdmin;
    }

    public KimUser(){

    }

    public Long getId() {
        return id;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public boolean isAdmin() {
        return isAdmin;
    }
}
