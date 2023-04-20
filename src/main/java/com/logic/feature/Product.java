package com.logic.feature;

class Product {
    /*Attribute */
    private int count;
    private String productName;
    private float basePrice;
    private float boughtPrice;
    private String category;

    /*Constructor */
    public Product(int count, String productName, float basePrice, float boughtPrice, String category){
        this.count = count;
        this.productName = productName;
        this.basePrice = basePrice;
        this.boughtPrice = boughtPrice;
        this.category = category;
    }

    /*Method */
    public String getName(){
        return this.productName;
    }
}