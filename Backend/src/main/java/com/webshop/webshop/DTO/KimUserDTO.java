package com.webshop.webshop.DTO;


import com.webshop.webshop.enums.Role;
import com.webshop.webshop.model.Address;
import com.webshop.webshop.model.KimUser;
import com.webshop.webshop.model.ShoppingCart;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class KimUserDTO {
    private Long userId;
    private String userName;
    private String userPassword;
    private String eMail;
    private String role;
    private String gender;
    private String firstName;
    private String lastName;
    private Address address;
    private List<ShoppingCart> shoppingCart;

    public KimUser convertToKimUser() {
        KimUser user = new KimUser(
                this.getUserName(),
                this.getUserPassword(),
                this.getEMail(),
                this.getGender(),
                this.getFirstName(),
                this.getLastName(),
                this.getAddress(),
                this.getShoppingCart());
        user.setRole(Role.valueOf(this.getRole()));
        return user;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }
}