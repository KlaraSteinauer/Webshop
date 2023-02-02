package com.webshop.webshop.model;

import jakarta.persistence.*;

@Entity(name = "user")
public class Operator {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Column(name = "lastname")
    private String lastName;
    @Column(name = "firstname")
    private String firstName;
    @Column(name = "username")
    private String userName;
    @Column(name = "password")
    private String userPassword;
    @Column(name = "admin")
    boolean isAdmin;

    public Operator(Long id, String lastName, String firstName, String userName, String userPassword, boolean isAdmin) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.userName = userName;
        this.userPassword = userPassword;
        this.isAdmin = isAdmin;
    }

    public Operator() {

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
