package com.pudding.final_project;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.pudding.final_project.entity.BaseEntity;
import com.pudding.final_project.util.PriceCalculator;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<OrderItem> items = new ArrayList<>();

    private double totalAmount;
    private LocalDateTime orderDate;
    private String status; // "PENDING", "COMPLETED", "CANCELED"

    // Default constructor required by JPA
    public Order() {
        this.orderDate = LocalDateTime.now();
        this.status = "PENDING";
    }

    // Getters and setters
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    public List<OrderItem> getItems() { return items; }
    public void setItems(List<OrderItem> items) { this.items = items; }
    public double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }
    public LocalDateTime getOrderDate() { return orderDate; }
    public void setOrderDate(LocalDateTime orderDate) { this.orderDate = orderDate; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public void addItem(OrderItem item) {
        items.add(item);
        item.setOrder(this);
        updateTotalAmount();
    }

    public void removeItem(OrderItem item) {
        items.remove(item);
        item.setOrder(null);
        updateTotalAmount();
    }

    private void updateTotalAmount() {
        this.totalAmount = PriceCalculator.calculateOrderTotal(items);
    }
} 