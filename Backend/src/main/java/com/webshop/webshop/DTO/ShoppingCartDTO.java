package com.webshop.webshop.DTO;

import com.webshop.webshop.model.KimUser;
import com.webshop.webshop.model.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShoppingCartDTO {
    private Long userId;
    private List<ProductDTO> products;
}
