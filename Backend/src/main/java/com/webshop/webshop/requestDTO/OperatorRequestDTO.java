package com.webshop.webshop.requestDTO;

import com.webshop.webshop.enums.EnumNamePattern;
import com.webshop.webshop.enums.Role;
import jakarta.validation.Constraint;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.Length;


public class OperatorRequestDTO {
    @NotBlank
    private String userName;
    @NotBlank
    @Length(min = 8, max = 16)
    private String userPassword;
    @EnumNamePattern(regexp = "NEW|DEFAULT")
    private Role userRole;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
}
