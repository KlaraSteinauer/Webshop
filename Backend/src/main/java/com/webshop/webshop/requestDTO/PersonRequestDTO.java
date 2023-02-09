package com.webshop.webshop.requestDTO;

import com.webshop.webshop.model.Address;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.Length;

public class PersonRequestDTO {
    @NotBlank // eingeschränkt durch drop down menu
    private String gender;
    @Pattern(regexp = "^[A-Za-z]*$")
    @Length(min=2, max = 64)
    private String firstname;
    @Length(min=2, max=64)
    private String lastname;
    @NotBlank
    private Address address;

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
