package com.pudding.final_project.controller;

import com.pudding.final_project.Cart;
import com.pudding.final_project.CartService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/cart")
public class CartController {
    private static final Logger logger = LoggerFactory.getLogger(CartController.class);

    @Autowired
    private CartService cartService;

    @PostMapping("/add")
    public ResponseEntity<?> addToCart(@RequestBody Map<String, Object> request) {
        try {
            logger.info("Adding item to cart: {}", request);
            Long userId = Long.valueOf(request.get("userId").toString());
            Long itemId = Long.valueOf(request.get("itemId").toString());
            Integer quantity = request.get("quantity") != null ? (Integer) request.get("quantity") : 1;

            Cart cart = cartService.addItemToCart(userId, itemId, quantity);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("cart", cart);
            logger.info("Successfully added item to cart: {}", cart);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Error adding item to cart: ", e);
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getCart(@PathVariable Long userId) {
        try {
            logger.info("Getting cart for user: {}", userId);
            Cart cart = cartService.getCart(userId);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("cart", cart);
            logger.info("Successfully retrieved cart: {}", cart);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Error getting cart: ", e);
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateCartItem(@RequestBody Map<String, Object> request) {
        try {
            logger.info("Updating cart item: {}", request);
            Long userId = Long.valueOf(request.get("userId").toString());
            Long itemId = Long.valueOf(request.get("itemId").toString());
            Integer quantity = (Integer) request.get("quantity");

            Cart cart = cartService.updateCartItem(userId, itemId, quantity);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("cart", cart);
            logger.info("Successfully updated cart: {}", cart);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Error updating cart item: ", e);
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @DeleteMapping("/remove/{userId}/{itemId}")
    public ResponseEntity<?> removeFromCart(@PathVariable Long userId, @PathVariable Long itemId) {
        try {
            logger.info("Removing item from cart: userId={}, itemId={}", userId, itemId);
            Cart cart = cartService.removeItemFromCart(userId, itemId);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("cart", cart);
            logger.info("Successfully removed item from cart: {}", cart);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Error removing item from cart: ", e);
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
} 