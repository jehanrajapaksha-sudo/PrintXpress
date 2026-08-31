package com.example.PrintXpress;

public class Product {
    private String id;
    private String name;
    private String description;
    private double basePrice;
    private int imageResourceId;

    public Product(String id, String name, String description, double basePrice, int imageResourceId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.basePrice = basePrice;
        this.imageResourceId = imageResourceId;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public double getBasePrice() { return basePrice; }
    public int getImageResourceId() { return imageResourceId; }
}