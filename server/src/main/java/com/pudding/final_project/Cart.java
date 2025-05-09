package com.pudding.final_project;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.pudding.final_project.entity.BaseEntity;
import com.pudding.final_project.util.PriceCalculator;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Cart extends BaseEntity {
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<CartItem> items = new ArrayList<>();

    private double totalAmount;

    // Getters and setters
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    public List<CartItem> getItems() { return items; }
    public void setItems(List<CartItem> items) { this.items = items; }
    public double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }

    public void addItem(CartItem item) {
        items.add(item);
        item.setCart(this);
        updateTotalAmount();
    }

    public void removeItem(CartItem item) {
        items.remove(item);
        item.setCart(null);
        updateTotalAmount();
    }

    private void updateTotalAmount() {
        this.totalAmount = PriceCalculator.calculateTotal(items);
    }
} 