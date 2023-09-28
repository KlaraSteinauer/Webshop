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
    private Role role;
    private String gender;
    private String firstname;
    private String lastname;
    private Address address;
    private List<ShoppingCart> shoppingCart;

    public KimUser convertToKimUser() {
        return new KimUser(
                this.getUserId(),
                this.getUserName(),
                this.getUserPassword(),
                this.getEMail(),
                this.getRole(),
                this.getGender(),
                this.getFirstname(),
                this.getLastname(),
                this.getAddress(),
                this.getShoppingCart());
    }

}