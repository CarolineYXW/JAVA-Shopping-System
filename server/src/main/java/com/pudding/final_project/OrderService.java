package com.pudding.final_project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CartService cartService;

    @Autowired
    private ItemService itemService;

    @Autowired
    private UserService userService;

    @Autowired
    private OrderItemService orderItemService;

    @Transactional
    public Order createOrder(Long userId) {
        // Get user's cart
        Cart cart = cartService.getCart(userId);
        if (cart.getItems().isEmpty()) {
            throw new RuntimeException("Cannot create order: Cart is empty");
        }

        // Create new order
        Order order = new Order();
        order.setUser(userService.findById(userId).orElseThrow());

        // Transfer items from cart to order
        for (CartItem cartItem : cart.getItems()) {
            Item item = cartItem.getItem();
            
            // Check stock again before creating order
            if (item.getStock() < cartItem.getQuantity()) {
                throw new RuntimeException("Insufficient stock for item: " + item.getName());
            }

            // Create order item
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setItem(item);
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPriceAtOrder(cartItem.getPrice());
            order.addItem(orderItem);

            // Update item stock
            item.setStock(item.getStock() - cartItem.getQuantity());
            itemService.save(item);
        }

        // Clear the cart
        cartService.clearAllCarts();

        // Save and return the order
        return orderRepository.save(order);
    }

    public List<Order> getOrdersByUserId(Long userId) {
        return orderRepository.findByUserIdOrderByOrderDateDesc(userId);
    }

    public Optional<Order> getOrderById(Long orderId) {
        return orderRepository.findById(orderId);
    }

    @Transactional
    public Order cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if ("CANCELED".equals(order.getStatus())) {
            throw new RuntimeException("Order is already canceled");
        }

        // Restore item stock
        for (OrderItem orderItem : order.getItems()) {
            Item item = orderItem.getItem();
            item.setStock(item.getStock() + orderItem.getQuantity());
            itemService.save(item);
        }

        order.setStatus("CANCELED");
        return orderRepository.save(order);
    }

    @Transactional
    public void deleteAll() {
        orderRepository.deleteAll();
    }
} 