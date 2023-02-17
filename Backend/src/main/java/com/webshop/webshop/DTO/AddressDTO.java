package com.webshop.webshop.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

public class AddressDTO {
    @NotEmpty
    private Long addressId;
    @NotEmpty
    @Pattern(regexp = "^[A-Za-z]*$")
    private String street;
    @NotEmpty
    private String number;
    @NotBlank
    @Length(min = 4, max = 4)
    private int zip;
    @NotEmpty
    @Pattern(regexp = "^[A-Za-z]*$")
    private String city;
    @NotEmpty
    @Pattern(regexp = "^[A-Za-z]*$")
    private String country;

    public AddressDTO(Long addressId, String street, String number, int zip, String city, String country) {
        this.addressId = addressId;
        this.street = street;
        this.number = number;
        this.zip = zip;
        this.city = city;
        this.country = country;
    }

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
