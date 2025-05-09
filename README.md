# Java Shopping System

A full-stack web-based shopping system built with Spring Boot and modern web technologies. This application provides a complete e-commerce experience with user authentication, product browsing, shopping cart functionality, and order management.

## Features

- **User Management**
  - User registration and login
  - Session management
  - User profile display

- **Product Management**
  - Browse products by category
  - Search functionality
  - Product details with images
  - Stock management

- **Shopping Cart**
  - Add/remove items
  - Update quantities
  - Real-time price calculation
  - Stock validation

- **Order System**
  - Create orders from cart
  - View order history
  - Cancel pending orders
  - Order status tracking

## Technologies Used

### Backend
- Java 17
- Spring Boot 3.2.3
- Spring Data JPA
- Hibernate
- MySQL Database
- Maven

### Frontend
- HTML5
- CSS3 (Bootstrap 5.3.0)
- JavaScript (ES6+)
- Thymeleaf Template Engine

## Prerequisites

Before running the application, ensure you have the following installed:
- Java 17 or higher
- Maven
- MySQL Server
- Git (for cloning the repository)

## Getting Started

1. **Clone the Repository**
   ```bash
   git clone https://github.com/CarolineYXW/JAVA-Shopping-System
   cd JAVA-Shopping-System
   ```

2. **Database Setup**
   - Create a MySQL database named `shopping_system`
   - The application will automatically create the necessary tables on startup

3. **Configure Database Connection**
   - Open `src/main/resources/application.properties`
   - Update the database connection properties if needed:
     ```properties
     spring.datasource.url=jdbc:mysql://localhost:3306/shopping_system
     spring.datasource.username=your_username
     spring.datasource.password=your_password
     ```

4. **Build and Run**
   ```bash
   cd server
   ./mvnw clean spring-boot:run
   ```
   The application will start on `http://localhost:8080`

## Default Users

The system comes with two default users for testing:
- Username: `user1`, Password: `password1`
- Username: `user2`, Password: `password2`

## Project Structure

```
server/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/pudding/final_project/
│   │   │       ├── controller/    # REST and web controllers
│   │   │       ├── model/         # Entity classes
│   │   │       ├── repository/    # Data access layer
│   │   │       ├── service/       # Business logic
│   │   │       └── config/        # Configuration classes
│   │   └── resources/
│   │       ├── static/           # Static resources (CSS, JS, images)
│   │       ├── templates/        # Thymeleaf templates
│   │       └── application.properties
│   └── test/                     # Test files
└── pom.xml                       # Maven configuration
```

## API Endpoints

### Authentication
- `POST /api/auth/login` - User login
- `POST /api/auth/register` - User registration

### Products
- `GET /api/items` - Get all products
- `GET /api/items/category/{category}` - Get products by category
- `GET /api/items/{id}` - Get product details

### Cart
- `GET /api/cart/{userId}` - Get user's cart
- `POST /api/cart/add` - Add item to cart
- `POST /api/cart/update` - Update cart item quantity
- `DELETE /api/cart/remove/{userId}/{itemId}` - Remove item from cart

### Orders
- `POST /api/orders/create/{userId}` - Create new order
- `GET /api/orders/user/{userId}` - Get user's orders
- `POST /api/orders/cancel/{orderId}` - Cancel order
