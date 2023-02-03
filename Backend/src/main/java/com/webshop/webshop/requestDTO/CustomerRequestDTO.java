package com.webshop.webshop.requestDTO;

import jakarta.validation.constraints.NotBlank;

public class CustomerRequestDTO {
    @NotBlank
    private String lastName;
    @NotBlank
    private String firstName;
    @NotBlank
    private String userName;
    @NotBlank
    private String userPassword;

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
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
}
