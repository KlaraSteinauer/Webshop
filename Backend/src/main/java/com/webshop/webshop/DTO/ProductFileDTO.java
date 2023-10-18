
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
        Product product = new Product();
        product.setId(this.getId());
        product.setName(this.getName());
        product.setDescription(this.getDescription());
        product.setImageUrl(this.getImage().getOriginalFilename());
        product.setPrice(this.getPrice());
        product.setQuantity(this.getQuantity());
        product.setCategory(ProductCategory.valueOf(this.getCategory()));
        return product;
    }

    public ProductViewDTO convertToProductViewDTO() {
        return new ProductViewDTO(
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
