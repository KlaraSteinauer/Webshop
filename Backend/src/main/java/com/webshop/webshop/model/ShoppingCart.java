package com.webshop.webshop.model;

import com.webshop.webshop.DTO.ShoppingCartDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "shopping_cart")
public class ShoppingCart {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long shoppingCartId;
    @OneToOne
    @JoinColumn(name = "kim_user_id", referencedColumnName = "id")
    private KimUser kimUser;
    @OneToOne(mappedBy = "shoppingCart")
    private KimOrder order;
    @OneToMany(mappedBy = "cart")
    private Set<Position> positions;

    public ShoppingCartDTO convertToDto() {
        return new ShoppingCartDTO(
                this.getKimUser().getUserId(),
                this.getPositions().stream()
                        .map(Position::convertToDto)
                        .collect(Collectors.toList())
        );
    }
}
