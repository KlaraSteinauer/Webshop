package com.webshop.webshop.model;

import com.webshop.webshop.DTO.ShoppingCartDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
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
    private Long id;
    @OneToOne(mappedBy = "shoppingCart")
    private KimUser kimUser;
    @OneToOne(mappedBy = "shoppingCart")
    private KimOrder order;
    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL)
    private Set<Position> positions;

    public ShoppingCartDTO convertToDto() {
        return new ShoppingCartDTO(
                this.getKimUser().getId(),
                this.getPositions().stream()
                        .map(Position::convertToDto)
                        .collect(Collectors.toList())
        );
    }

    public void addPosition(Position position) {
        if (this.positions == null) {
            setPositions(new HashSet<>());
        }
        if (!this.positions.contains(position)) {
            positions.add(position);
        }
        position.setCart(this);
    }

    public void removePosition(Position position) {
        if (this.positions != null) {
            this.positions.remove(position);
        }
    }

    public Integer countItems() {
        int itemCount = 0;
        Set<Position> positions = this.getPositions();
        if (positions != null) {
            for (Position position : positions) {
                itemCount += position.getQuantity();
            }
        }
        return itemCount;
    }
}
