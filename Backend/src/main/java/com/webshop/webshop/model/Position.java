package com.webshop.webshop.model;


import com.webshop.webshop.DTO.PositionDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "position")
public class Position {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "position_id")
    private Long id;

    @ManyToOne
    @Column(name = "cart_id")
    private ShoppingCart cart;

    @ManyToOne
    @Column(name ="product_id")
    private Product product;

    @Column(name = "quantity")
    private Integer quantity;

    public PositionDTO convertToDto() {
        return new PositionDTO(
                this.getProduct().convertToDto(),
                this.getQuantity()

        );
    }
}
