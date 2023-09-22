package com.webshop.webshop.DTO;


import com.webshop.webshop.model.Address;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;


public class KimUserDTO {
    private String userName;
    private String userPassword;
    private String eMail;
    // private Role userRole;
    private String gender;
    private String firstname;
    private String lastname;
    private Address address;

    public KimUserDTO(String userName, String userPassword, String eMail, String gender, String firstname, String lastname, Address address) {
        this.userName = userName;
        this.userPassword = userPassword;
        this.eMail = eMail;
        this.gender = gender;
        this.firstname = firstname;
        this.lastname = lastname;
        this.address = address;
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}