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
        
        // 1. Clear cart items first
        logger.info("Clearing cart items...");
        cartService.clearAllCarts();
        
        // 2. Clear order items
        logger.info("Clearing order items...");
        orderItemService.deleteAll();
        
        // 3. Clear orders
        logger.info("Clearing orders...");
        orderService.deleteAll();
        
        // 4. Clear items
        logger.info("Clearing items...");
        itemService.deleteAll();

        // Initialize new items
        logger.info("Initializing new items...");
        
        // Food items
        Item crab = new Item();
        crab.setName("Crab");
        crab.setPrice(29.99);
        crab.setStock(10);
        crab.setImagePath("/images/crab.jpg");
        crab.setCategory("Food");
        itemService.createItem(crab);

        Item biscuit = new Item();
        biscuit.setName("Biscuit");
        biscuit.setPrice(1.99);
        biscuit.setStock(20);
        biscuit.setImagePath("/images/biscuit.jpg");
        biscuit.setCategory("Food");
        itemService.createItem(biscuit);

        Item corn = new Item();
        corn.setName("Corn");
        corn.setPrice(2.99);
        corn.setStock(15);
        corn.setImagePath("/images/corn.jpg");
        corn.setCategory("Food");
        itemService.createItem(corn);

        Item chicken = new Item();
        chicken.setName("Chicken");
        chicken.setPrice(18.99);
        chicken.setStock(8);
        chicken.setImagePath("/images/chicken.jpg");
        chicken.setCategory("Food");
        itemService.createItem(chicken);

        Item tart = new Item();
        tart.setName("Tart");
        tart.setPrice(3.99);
        tart.setStock(8);
        tart.setImagePath("/images/tart.jpg");
        tart.setCategory("Food");
        itemService.createItem(tart);

        Item sukiyaki = new Item();
        sukiyaki.setName("Sukiyaki");
        sukiyaki.setPrice(35.99);
        sukiyaki.setStock(5);
        sukiyaki.setImagePath("/images/sukiyaki.jpg");
        sukiyaki.setCategory("Food");
        itemService.createItem(sukiyaki);

        Item bagel = new Item();
        bagel.setName("Bagel");
        bagel.setPrice(12.99);
        bagel.setStock(25);
        bagel.setImagePath("/images/bagel.jpg");
        bagel.setCategory("Food");
        itemService.createItem(bagel);

        Item milktea = new Item();
        milktea.setName("Milk Tea");
        milktea.setPrice(6.99);
        milktea.setStock(15);
        milktea.setImagePath("/images/milktea.jpg");
        milktea.setCategory("Food");
        itemService.createItem(milktea);

        // Non-food items
        Item plush = new Item();
        plush.setName("Plush");
        plush.setPrice(69.99);
        plush.setStock(5);
        plush.setImagePath("/images/plush.jpg");
        plush.setCategory("Toys");
        itemService.createItem(plush);

        Item drawing = new Item();
        drawing.setName("Drawing");
        drawing.setPrice(99.99);
        drawing.setStock(1);
        drawing.setImagePath("/images/drawing.jpg");
        drawing.setCategory("Art");
        itemService.createItem(drawing);

        Item cup = new Item();
        cup.setName("Cup");
        cup.setPrice(9.99);
        cup.setStock(10);
        cup.setImagePath("/images/cup.jpg");
        cup.setCategory("Home");
        itemService.createItem(cup);

        logger.info("Items created successfully");
    }
} 