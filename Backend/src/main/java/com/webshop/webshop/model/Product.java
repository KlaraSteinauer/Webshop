package com.webshop.webshop.model;

public class Product {
    private final Long id;
    private String productName;
    private String productDescription;
    private String productImageUrl;
    private double productPrice;

    public Product(Long id, String productName, String productDescription, String productImageUrl, double productPrice) {
        this.id = id;
        this.productName = productName;
        this.productDescription = productDescription;
        this.productImageUrl = productImageUrl;
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
