package com.webshop.webshop.DTO;


import com.webshop.webshop.enums.Role;
import com.webshop.webshop.model.Address;
import com.webshop.webshop.model.KimUser;
import com.webshop.webshop.model.ShoppingCart;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class KimUserDTO {
    private Long userId;
    private String userName;
    private String userPassword;
    private String userEmail;
    private String role = Role.CUSTOMER.name();
    private String gender;
    private String firstName;
    private String lastName;

    public KimUser convertToKimUser() {
        KimUser user = new KimUser();
        user.setUserName(this.getUserName());
        user.setUserPassword(this.getUserPassword());
        user.setUserEmail(this.getUserEmail());
        user.setGender(this.getGender());
        user.setFirstName(this.getFirstName());
        user.setLastName(this.getLastName());
        user.setRole(Role.valueOf(this.getRole()));
        return user;
    }

    /*public void setFirstName(String firstName) {
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
    }*/
}