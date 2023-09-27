package com.webshop.webshop.model;

import com.webshop.webshop.DTO.AddressDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.context.annotation.Lazy;

/*
 * Example of oneToOne relation
 * https://www.baeldung.com/jpa-one-to-one
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "address")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Column(name = "street")
    private String street;
    @Column(name = "number")
    private String number;
    @Column(name = "zip")
    private int zip;
    @Column(name = "city")
    private String city;
    @Column(name = "country")
    private String country;
    @OneToOne
    @Lazy
    @JoinColumn(name = "resident_id", referencedColumnName = "id")
    private KimUser resident;

    public AddressDTO convertToDto() {
        return new AddressDTO(
                this.getStreet(),
                this.getNumber(),
                this.getZip(),
                this.getCity(),
                this.getCountry()
        );
    }
}
