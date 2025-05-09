package com.pudding.final_project.util;

import com.pudding.final_project.CartItem;
import com.pudding.final_project.OrderItem;
import java.util.List;

public class PriceCalculator {
    public static double calculateTotal(List<? extends CartItem> items) {
        return items.stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity())
                .sum();
    }

    public static double calculateOrderTotal(List<OrderItem> items) {
        return items.stream()
                .mapToDouble(item -> item.getPriceAtOrder() * item.getQuantity())
                .sum();
    }
} 