package com.pudding.final_project.controller;

import com.pudding.final_project.Cart;
import com.pudding.final_project.CartService;
import com.pudding.final_project.Item;
import com.pudding.final_project.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class WebController {

    @Autowired
    private ItemService itemService;

    @Autowired
    private CartService cartService;

    @GetMapping("/")
    public String home(Model model) {
        List<Item> items = itemService.findAll();
        model.addAttribute("items", items);
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @GetMapping("/cart")
    public String cart(Model model) {
        // For now, we'll use a default user ID of 1
        // In a real application, this would come from the authenticated user
        Long userId = 1L;
        Cart cart = cartService.getCart(userId);
        model.addAttribute("cart", cart);
        return "cart";
    }

    @GetMapping("/order-history")
    public String orderHistory() {
        return "order-history";
    }
} 