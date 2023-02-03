package com.webshop.webshop.model;

import jakarta.persistence.*;

@Entity(name = "customer")
public class CustomerModel extends UserModel{
    @Column(name = "lastname")
    private String lastName;
    @Column(name = "firstname")
    private String firstName;
    @Column(name = "password")
    private String userPassword;


 


}
