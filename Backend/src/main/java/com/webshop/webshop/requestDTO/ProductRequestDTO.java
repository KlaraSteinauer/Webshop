package com.webshop.webshop.requestDTO;

import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.Length;

//Spiegelobject von Model(Entity) das ans Frontend bzw. vom Frontend gesendet wird.
//1. Informationen werden als neues Model erstellt und an die Datenbank weiterübertragen. (Service!)
//2. Informationen vom Model(Datenbank) werden in ein DTO übertragen und im Frontend angezeit. (Service!)
//3. Im DTO findet die validierung der Attribute statt! NICHT im Model.
public class ProductRequestDTO {
    @NotBlank
    @Length(min = 2, max = 64)
    private String productName;
    @Length(max = 512)
    private String productDescription;
    private String productImageUrl;
    @NotBlank
    @DecimalMin("0.01")
    private double productPrice;
    @NotBlank
    @Min(0)
    private int productQuantity;
    @NotBlank
    //@Enum
    private String productCategory;


    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getProductImageUrl() {
        return productImageUrl;
    }

    public void setProductImageUrl(String productImageUrl) {
        this.productImageUrl = productImageUrl;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }
}
