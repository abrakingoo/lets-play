package com.lets_play_e_comerce_platform.lets_play;

public class Product {
    String id;
    String name;
    String description;
    Double price;
    String userId;

    public Product(String id, String name, String description, Double price, String userId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.userId = userId;
    }
    
}
