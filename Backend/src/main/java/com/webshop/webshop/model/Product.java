package com.webshop.webshop.model;

import jakarta.persistence.*;

//Ist das Kernobjekt der Datenbank.
//Gibt mir die gespeicherten Werte der Datenbank oder fügt/ändert neue Objekte zur Datenbank. ("Vorlage" für Datenbankobjekte)

// TODO implement lombok
@Entity(name="product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private Long id;
    @Column(name="name")
    private String productName;
    @Column(name="description")
    private String productDescription;
    @Column(name="image_url")
    private String productImageUrl;
    @Column(name="price")
    private double productPrice;

    public Product(String productName, String productDescription, String productImageUrl, double productPrice) {
        this.productName = productName;
        this.productDescription = productDescription;
        this.productImageUrl = productImageUrl;
        this.productPrice = productPrice;
    }

    public Product(){

    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public void setProductImageUrl(String productImageUrl) {
        this.productImageUrl = productImageUrl;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }
    public Long getId() {
        return id;
    }

    public String getProductName() {
        return productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public String getProductImageUrl() {
        return productImageUrl;
    }

    public double getProductPrice() {
        return productPrice;
    }
}