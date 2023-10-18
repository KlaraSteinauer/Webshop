package com.webshop.webshop.model;

import com.webshop.webshop.DTO.KimUserDTO;
import com.webshop.webshop.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Entity(name = "kim_user")
public class KimUser  {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "user_name", unique = true)
    private String userName;
    @Column(name = "user_password")
    private String userPassword;
    @Column(name = "e-mail")
    private String userEmail;

    @Enumerated(value = EnumType.ORDINAL)
    @NotNull
    @Column(name = "user_role")
    private Role role = Role.CUSTOMER;
    @Column(name = "gender")
    private String gender;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @OneToOne(cascade = CascadeType.ALL, optional = true)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cart_id", referencedColumnName = "id")
    private ShoppingCart shoppingCart;

    public KimUser(String userName, String userPassword, String userEmail, String gender, String firstName, String lastName, Address address, ShoppingCart shoppingCart) {
        this.userName = userName;
        this.userPassword = userPassword;
        this.userEmail = userEmail;
        this.gender = gender;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.shoppingCart = shoppingCart;
    }

    public KimUserDTO convertToDto() {
        return new KimUserDTO(
                this.getId(),
                this.getUserName(),
                this.getUserPassword(),
                this.getUserEmail(),
                this.getRole().name(),
                this.getGender(),
                this.getFirstName(),
                this.getLastName());
    }
}
