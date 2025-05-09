package com.pudding.final_project;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class DataInitializer implements CommandLineRunner {
    private static final Logger logger = LoggerFactory.getLogger(DataInitializer.class);
    private final UserService userService;
    private final ItemService itemService;
    private final CartService cartService;
    private final OrderItemService orderItemService;
    private final OrderService orderService;

    public DataInitializer(UserService userService, ItemService itemService, CartService cartService, 
                          OrderItemService orderItemService, OrderService orderService) {
        this.userService = userService;
        this.itemService = itemService;
        this.cartService = cartService;
        this.orderItemService = orderItemService;
        this.orderService = orderService;
    }

    private Item createItem(String name, double price, int stock, String imagePath, String category) {
        Item item = new Item();
        item.setName(name);
        item.setPrice(price);
        item.setStock(stock);
        item.setImagePath(imagePath);
        item.setCategory(category);
        return itemService.save(item);
    }

    @Override
    @Transactional
    public void run(String... args) {
        logger.info("Starting data initialization...");
        
        // Initialize sample users if none exist
        if (userService.getAllUsers().isEmpty()) {
            User user1 = new User();
            user1.setUsername("testuser1");
            user1.setPassword("testpass1");
            userService.createUser(user1);

            User user2 = new User();
            user2.setUsername("testuser2");
            user2.setPassword("testpass2");
            userService.createUser(user2);
        }

        // Clear all data in the correct order to respect foreign key constraints
        logger.info("Clearing all data...");
        cartService.clearAllCarts();
        orderItemService.deleteAll();
        orderService.deleteAll();
        itemService.deleteAll();

        // Initialize new items
        logger.info("Initializing new items...");
        
        // Food items
        createItem("Crab", 29.99, 10, "/images/crab.jpg", "Food");
        createItem("Biscuit", 1.99, 20, "/images/biscuit.jpg", "Food");
        createItem("Corn", 2.99, 15, "/images/corn.jpg", "Food");
        createItem("Chicken", 18.99, 8, "/images/chicken.jpg", "Food");
        createItem("Tart", 3.99, 8, "/images/tart.jpg", "Food");
        createItem("Sukiyaki", 35.99, 5, "/images/sukiyaki.jpg", "Food");
        createItem("Bagel", 15.99, 5, "/images/bagel.jpg", "Food");
        createItem("Milk Tea", 6.99, 15, "/images/milktea.jpg", "Food");

        // Non-food items
        createItem("Plush", 69.99, 5, "/images/plush.jpg", "Toys");
        createItem("Drawing", 99.99, 1, "/images/drawing.jpg", "Art");
        createItem("Cup", 10.99, 10, "/images/cup.jpg", "Home");

        logger.info("Items created successfully");
    }
} 