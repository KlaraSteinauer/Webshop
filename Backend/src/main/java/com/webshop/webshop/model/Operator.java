package com.webshop.webshop.model;

import jakarta.persistence.*;

@Entity(name = "user")
public class Operator {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Column(name = "username")
    private String userName;

    public Operator(Long id, String userName) {
        this.id = id;
        this.userName = userName;
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
}
