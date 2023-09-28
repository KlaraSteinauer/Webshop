package com.webshop.webshop.enums;

public enum ProductCategory {

    SALZPFEFFER(1),
    KRAEUTER(2),
    SUESSMITTEL(2);
    public final int value;
    private ProductCategory(int value) {
        this.value = value;
    }



}
