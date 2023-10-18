package com.webshop.webshop.model;

import com.webshop.webshop.DTO.ProductViewDTO;
import com.webshop.webshop.enums.ProductCategory;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

//Ist das Kernobjekt der Datenbank.
//Gibt mir die gespeicherten Werte der Datenbank oder fügt/ändert neue Objekte zur Datenbank. ("Vorlage" für Datenbankobjekte)

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "image_url")
    private String imageUrl;
    @Column(name = "price")
    private double price;
    @Column(name = "quantity")
    private int quantity;
    @Column(name = "category")
    private ProductCategory category;
    @OneToMany(mappedBy = "product")
    private Set<Position> positions;
    public ProductViewDTO convertToViewDto() {
        return new ProductViewDTO(
                this.getId(),
                this.getName(),
                this.getDescription(),
                this.getImageUrl(),
                this.getPrice(),
                this.getQuantity(),
                this.getCategory().toString()
        );
    }

}