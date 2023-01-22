package com.webshop.webshop.model;

import jakarta.persistence.*;

// TODO implement lombok
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column
    private String productName;
    @Column
    private String productDescription;
    @Column
    private String productImageUrl;
    @Column
    private double productPrice;

    public Product(String productName, String productDescription, String productImageUrl, double productPrice) {
        this.productName = productName;
        this.productDescription = productDescription;
        this.productImageUrl = productImageUrl;
        this.productPrice = productPrice;
    }

    public Product(){

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
