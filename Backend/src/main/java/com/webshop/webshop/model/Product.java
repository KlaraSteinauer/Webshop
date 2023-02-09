package com.webshop.webshop.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.Length;

//Ist das Kernobjekt der Datenbank.
//Gibt mir die gespeicherten Werte der Datenbank oder fügt/ändert neue Objekte zur Datenbank. ("Vorlage" für Datenbankobjekte)

// TODO implement lombok
@Entity(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "productID")
    private Long id;
    @Column(name = "name")
    private String productTitle;
    @Column(name = "description")
    private String productDescription;
    @Column(name = "imageURL")
    private String productImageUrl;
    @Column(name = "price")
    private double productPrice;
    @Column(name = "amount")
    private int productAmount;
    @Column(name = "category")
    private String productCategory;

    public Product(String productTitle, String productDescription, String productImageUrl, double productPrice, int productAmount, String productCategory) {
        this.productTitle = productTitle;
        this.productDescription = productDescription;
        this.productImageUrl = productImageUrl;
        this.productPrice = productPrice;
        this.productAmount = productAmount;
        this.productCategory = productCategory;
    }

    public Product() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
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

    public int getProductAmount() {
        return productAmount;
    }

    public void setProductAmount(int productAmount) {
        this.productAmount = productAmount;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }
}