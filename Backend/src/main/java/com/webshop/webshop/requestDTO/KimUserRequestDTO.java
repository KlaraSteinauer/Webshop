package com.webshop.webshop.requestDTO;


import com.webshop.webshop.model.Address;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.Length;


public class KimUserRequestDTO {
    @NotBlank
    @Length(min=2, max=12)
    private String userName;
    @NotBlank
    @Length(min=4, max=16)
    private String userPassword;
    @NotBlank
    @Email
    private String eMail;
    // TODO @Enum
    // private Role userRole;
    @NotBlank
    private String gender;
    @Pattern(regexp = "^[A-Za-z]*$")
    @Length(min=2, max=64)
    private String firstname;
    @Pattern(regexp = "^[A-Za-z]*$")
    @Length(min=2, max=64)
    private String lastname;
    @NotBlank
    private Address address;

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