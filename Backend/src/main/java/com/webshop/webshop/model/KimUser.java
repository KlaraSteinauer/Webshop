package com.webshop.webshop.model;

import com.webshop.webshop.enums.Role;
import jakarta.persistence.*;

@Entity(name = "user")
public class KimUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private Long kimUserId;
    @Column(name = "user_name")
    private String userName;
    @Column(name = "user_password")
    private String userPassword;
    @Column(name = "e-mail")
    private String eMail;
    @Column(name = "user_role")
    private Role userRole;
    @Column(name = "gender")
    private String gender;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    //@Column(name = "address")
    @JoinColumn(name="addressId")
    @OneToOne
    private Address address;

    public KimUser(Long kimUserId, String userName, String userPassword, String eMail, Role userRole,
                   String gender, String firstName, String lastName, Address address) {
        this.kimUserId = kimUserId;
        this.userName = userName;
        this.userPassword = userPassword;
        this.eMail = eMail;
        this.userRole = userRole;
        this.gender = gender;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
    }


    public KimUser() {
    }

    public Long getKimUserId() {
        return kimUserId;
    }

    public void setKimUserId(Long kimUserId) {
        this.kimUserId = kimUserId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public Role getUserRole() {
        return userRole;
    }

    public void setUserRole(Role userRole) {
        this.userRole = userRole;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

}
