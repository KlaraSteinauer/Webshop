package com.webshop.webshop.model;

import com.webshop.webshop.DTO.KimUserDTO;
import com.webshop.webshop.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@AllArgsConstructor
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

    public KimUserDTO convertToDto() {
        return new KimUserDTO(
                this.getUserName(),
                this.getUserPassword(),
                this.getEMail(),
                this.getGender(),
                this.getFirstName(),
                this.getLastName(),
                this.getAddress()
        );
    }
}
