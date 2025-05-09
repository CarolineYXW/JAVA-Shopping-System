package com.pudding.final_project;

import com.pudding.final_project.entity.BaseEntity;
import jakarta.persistence.Entity;

@Entity
public class Item extends BaseEntity {
    private String name;
    private double price;
    private int stock;
    private String imagePath;
    private String category;

    // Default constructor required by JPA
    public Item() {}

    // Constructor for creating items
    public Item(String name, double price, int stock, String imagePath, String category) {
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.imagePath = imagePath;
        this.category = category;
    }

    // Getters and setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }
    public String getImagePath() { return imagePath; }
    public void setImagePath(String imagePath) { this.imagePath = imagePath; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
} 