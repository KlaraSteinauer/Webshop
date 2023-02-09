package com.webshop.webshop.model;

import jakarta.persistence.*;

//Ist das Kernobjekt der Datenbank.
//Gibt mir die gespeicherten Werte der Datenbank oder fügt/ändert neue Objekte zur Datenbank. ("Vorlage" für Datenbankobjekte)

@Entity(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "product_id")
    private Long productId;
    @Column(name = "name")
    private String productName;
    @Column(name = "description")
    private String productDescription;
    @Column(name = "image_url")
    private String productImageUrl;
    @Column(name = "price")
    private double productPrice;
    @Column(name = "quantity")
    private int productQuantity;
    @Column(name = "category")
    private String productCategory;
    /*
    private active ?
    private taxrate ?
     */

    public Product(Long productId, String productName, String productDescription, String productImageUrl,
                   double productPrice, int productQuantity, String productCategory) {
        this.productId = productId;
        this.productName = productName;
        this.productDescription = productDescription;
        this.productImageUrl = productImageUrl;
        this.productPrice = productPrice;
        this.productQuantity = productQuantity;
        this.productCategory = productCategory;
    }

    public Product() {

    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

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