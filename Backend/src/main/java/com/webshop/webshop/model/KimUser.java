package com.webshop.webshop.model;

import com.webshop.webshop.DTO.KimUserDTO;
import com.webshop.webshop.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity(name = "kim_user")
public class KimUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long userId;
    @Column(name = "user_name")
    private String userName;
    @Column(name = "user_password")
    private String userPassword;
    @Column(name = "e-mail")
    private String eMail;
    @NotNull
    @Column(name = "user_role")
    private Role role;
    @Column(name = "gender")
    private String gender;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @OneToOne(cascade = CascadeType.ALL, optional = true)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "id")
    private List<ShoppingCart> shoppingCart;

    public KimUser(Long userId, String userName, String userPassword, String eMail, Role role, String gender, String firstName, String lastName, Address address, List<ShoppingCart> shoppingCart) {
        this.userId = userId;
        this.userName = userName;
        this.userPassword = userPassword;
        this.eMail = eMail;
        this.role = Role.CUSTOMER;
        this.gender = gender;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.shoppingCart = shoppingCart;
    }

    public KimUserDTO convertToDto() {
        return new KimUserDTO(
                this.getUserId(),
                this.getUserName(),
                this.getUserPassword(),
                this.getEMail(),
                this.getRole(),
                this.getGender(),
                this.getFirstName(),
                this.getLastName(),
                this.getAddress(),
                this.getShoppingCart());
    }
}
