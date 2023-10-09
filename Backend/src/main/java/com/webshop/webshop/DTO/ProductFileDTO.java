
package com.webshop.webshop.DTO;

import com.webshop.webshop.enums.ProductCategory;
import com.webshop.webshop.model.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

//Spiegelobject von Model(Entity) das ans Frontend bzw. vom Frontend gesendet wird.
//1. Informationen werden als neues Model erstellt und an die Datenbank weiterübertragen. (Service!)
//2. Informationen vom Model(Datenbank) werden in ein DTO übertragen und im Frontend angezeit. (Service!)
//3. Im DTO findet die validierung der Attribute statt! NICHT im Model.
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductFileDTO {
    private Long id;
    private String name;
    private String description;
    private double price;
    private int quantity;
    private String category;
    private MultipartFile image;

    public Product convertToProduct() {
        return new Product(
                this.getId(),
                this.getName(),
                this.getDescription(),
                this.getImage().getOriginalFilename(),
                this.getPrice(),
                this.getQuantity(),
                ProductCategory.valueOf(this.getCategory()),
                null
        );
    }

    public ProductDTO convertToProductDTO() {
        return new ProductDTO(
                this.getId(),
                this.getName(),
                this.getDescription(),
                this.getImage().getOriginalFilename(),
                this.getPrice(),
                this.getQuantity(),
                this.getCategory()
        );
    }
}
