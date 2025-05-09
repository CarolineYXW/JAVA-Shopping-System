package com.pudding.final_project;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.pudding.final_project.entity.BaseEntity;
import jakarta.persistence.*;

@Entity
public class CartItem extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "cart_id")
    @JsonBackReference
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    private int quantity;
    private double price;

    // Getters and setters
    public Cart getCart() { return cart; }
    public void setCart(Cart cart) { this.cart = cart; }
    public Item getItem() { return item; }
    public void setItem(Item item) { this.item = item; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
} 