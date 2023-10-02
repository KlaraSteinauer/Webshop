package com.webshop.webshop.DTO;

import com.webshop.webshop.model.Product;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrdertDTO {

    @NotNull
    @PositiveOrZero
    private Product product;

    @NotNull
    @PositiveOrZero
    private Integer quantity;

}
