package com.webshop.webshop.DTO;

import com.webshop.webshop.model.Product;
import com.webshop.webshop.model.ShoppingCart;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PositionDTO {

    private ProductDTO product;

    private Integer quantity;


}
