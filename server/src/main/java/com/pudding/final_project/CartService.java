package com.pudding.final_project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CartService {
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ItemService itemService;

    @Autowired
    private UserService userService;

    public Cart getOrCreateCart(Long userId) {
        return cartRepository.findByUserId(userId)
                .orElseGet(() -> {
                    Cart cart = new Cart();
                    cart.setUser(userService.findById(userId).orElseThrow());
                    return cartRepository.save(cart);
                });
    }

    @Transactional
    public Cart addItemToCart(Long userId, Long itemId, int quantity) {
        Cart cart = getOrCreateCart(userId);
        Item item = itemService.findById(itemId).orElseThrow();

        // Get current quantity in cart
        int currentQuantity = cartItemRepository.findByCartIdAndItemId(cart.getId(), itemId)
                .map(CartItem::getQuantity)
                .orElse(0);

        // Check if total quantity (current + new) exceeds stock
        if (item.getStock() < currentQuantity + quantity) {
            throw new RuntimeException("Insufficient stock. Available: " + item.getStock() + ", Requested: " + (currentQuantity + quantity));
        }

        Optional<CartItem> existingItem = cartItemRepository.findByCartIdAndItemId(cart.getId(), itemId);
        if (existingItem.isPresent()) {
            CartItem cartItem = existingItem.get();
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
            cartItem.setPrice(item.getPrice());
            cartItemRepository.save(cartItem);
        } else {
            CartItem cartItem = new CartItem();
            cartItem.setCart(cart);
            cartItem.setItem(item);
            cartItem.setQuantity(quantity);
            cartItem.setPrice(item.getPrice());
            cart.addItem(cartItem);
            cartItemRepository.save(cartItem);
        }

        return cartRepository.save(cart);
    }

    @Transactional
    public Cart updateCartItem(Long userId, Long itemId, int quantity) {
        Cart cart = getOrCreateCart(userId);
        Item item = itemService.findById(itemId).orElseThrow();

        if (item.getStock() < quantity) {
            throw new RuntimeException("Insufficient stock. Available: " + item.getStock() + ", Requested: " + quantity);
        }

        CartItem cartItem = cartItemRepository.findByCartIdAndItemId(cart.getId(), itemId)
                .orElseThrow();
        cartItem.setQuantity(quantity);
        cartItem.setPrice(item.getPrice());
        cartItemRepository.save(cartItem);

        return cartRepository.save(cart);
    }

    @Transactional
    public Cart removeItemFromCart(Long userId, Long itemId) {
        Cart cart = getOrCreateCart(userId);
        CartItem cartItem = cartItemRepository.findByCartIdAndItemId(cart.getId(), itemId)
                .orElseThrow();
        cart.removeItem(cartItem);
        cartItemRepository.delete(cartItem);
        return cartRepository.save(cart);
    }

    public Cart getCart(Long userId) {
        return getOrCreateCart(userId);
    }

    @Transactional
    public void clearAllCarts() {
        cartItemRepository.deleteAll();
        cartRepository.deleteAll();
    }
} 