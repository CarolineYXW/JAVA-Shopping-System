package com.pudding.final_project.controller;

import com.pudding.final_project.Order;
import com.pudding.final_project.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private OrderService orderService;

    @PostMapping("/create/{userId}")
    public ResponseEntity<?> createOrder(@PathVariable Long userId) {
        try {
            logger.info("Creating order for user: {}", userId);
            Order order = orderService.createOrder(userId);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("order", order);
            response.put("message", "We have received your order. Thank you for your purchase!");
            logger.info("Successfully created order: {}", order);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Error creating order: ", e);
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getOrdersByUserId(@PathVariable Long userId) {
        try {
            logger.info("Getting orders for user: {}", userId);
            List<Order> orders = orderService.getOrdersByUserId(userId);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("orders", orders);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Error getting orders: ", e);
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping("/cancel/{orderId}")
    public ResponseEntity<?> cancelOrder(@PathVariable Long orderId) {
        try {
            logger.info("Canceling order: {}", orderId);
            Order order = orderService.cancelOrder(orderId);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("order", order);
            response.put("message", "Order has been canceled successfully");
            logger.info("Successfully canceled order: {}", order);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Error canceling order: ", e);
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
} 