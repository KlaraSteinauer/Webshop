package com.webshop.webshop.DTO;

import com.webshop.webshop.enums.ProductCategory;
import com.webshop.webshop.model.Product;

//Spiegelobject von Model(Entity) das ans Frontend bzw. vom Frontend gesendet wird.
//1. Informationen werden als neues Model erstellt und an die Datenbank weiterübertragen. (Service!)
//2. Informationen vom Model(Datenbank) werden in ein DTO übertragen und im Frontend angezeit. (Service!)
//3. Im DTO findet die validierung der Attribute statt! NICHT im Model.
public class ProductDTO {
    private Long id;
    private String name;
    private String description;
    private String imageUrl;
    private double price;
    private int quantity;
    private String category;

    public ProductDTO() {
    }

    public ProductDTO(Long id, String name, String description, String imageUrl, double price, int quantity,
                      String category) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
        this.price = price;
        this.quantity = quantity;
        this.category = category;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        imageUrl = imageUrl;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        quantity = quantity;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        category = category;
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
