package com.webshop.webshop.DTO;

import com.webshop.webshop.enums.ProductCategory;
import com.webshop.webshop.model.Product;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

//Spiegelobject von Model(Entity) das ans Frontend bzw. vom Frontend gesendet wird.
//1. Informationen werden als neues Model erstellt und an die Datenbank weiterübertragen. (Service!)
//2. Informationen vom Model(Datenbank) werden in ein DTO übertragen und im Frontend angezeit. (Service!)
//3. Im DTO findet die validierung der Attribute statt! NICHT im Model.
public class ProductDTO {
    private Long Id;
    private String Name;
    private String Description;
    private String ImageUrl;
    private double Price;
    private int Quantity;
    private String Category;

    public ProductDTO() {
    }

    public ProductDTO(Long id, String name, String description, String imageUrl, double price, int quantity,
                      String category) {
        Id = id;
        Name = name;
        Description = description;
        ImageUrl = imageUrl;
        Price = price;
        Quantity = quantity;
        Category = category;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public double getPrice() {
        return Price;
    }

    public void setPrice(double price) {
        Price = price;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public Product convertToProduct() {
        return new Product(
                this.getId(),
                this.getName(),
                this.getDescription(),
                this.getImageUrl(),
                this.getPrice(),
                this.getQuantity(),
                ProductCategory.valueOf(this.getCategory()),
                null
        );
    }
}
