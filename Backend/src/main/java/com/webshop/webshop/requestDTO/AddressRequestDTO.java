package com.webshop.webshop.requestDTO;

import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.Length;

public class AddressRequestDTO {
    @NotEmpty
    @Pattern(regexp = "^[A-Za-z]*$")
    private String street;
    @NotEmpty //liest Leerzeichen
    private String number;
    @NotBlank
    @Length(min=4,max=4)
    private int zip;
    @NotEmpty
    @Pattern(regexp = "^[A-Za-z]*$")
    private String city;
    @NotEmpty
    @Pattern(regexp = "^[A-Za-z]*$")
    private String country;

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getZip() {
        return zip;
    }

    public void setZip(int zip) {
        this.zip = zip;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
