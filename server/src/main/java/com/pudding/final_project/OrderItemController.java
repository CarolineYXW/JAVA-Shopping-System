package com.pudding.final_project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/order-items")
public class OrderItemController {
    @Autowired
    private OrderItemService orderItemService;

    @GetMapping
    public List<OrderItem> getAllOrderItems() {
        return orderItemService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderItem> getOrderItemById(@PathVariable Long id) {
        Optional<OrderItem> orderItem = orderItemService.findById(id);
        return orderItem.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public OrderItem createOrderItem(@RequestBody OrderItem orderItem) {
        return orderItemService.save(orderItem);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderItem> updateOrderItem(@PathVariable Long id, @RequestBody OrderItem orderItemDetails) {
        Optional<OrderItem> orderItem = orderItemService.findById(id);
        if (orderItem.isPresent()) {
            OrderItem existingOrderItem = orderItem.get();
            existingOrderItem.setOrder(orderItemDetails.getOrder());
            existingOrderItem.setItem(orderItemDetails.getItem());
            existingOrderItem.setQuantity(orderItemDetails.getQuantity());
            existingOrderItem.setPriceAtOrder(orderItemDetails.getPriceAtOrder());
            return ResponseEntity.ok(orderItemService.save(existingOrderItem));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrderItem(@PathVariable Long id) {
        Optional<OrderItem> orderItem = orderItemService.findById(id);
        if (orderItem.isPresent()) {
            orderItemService.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
} 