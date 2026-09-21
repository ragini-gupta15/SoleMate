# SoleMate

A full-stack shoe shopping, inventory, and order management platform built with Java, Spring Boot, MySQL, and vanilla JavaScript.

## Project Overview

SoleMate is a modern footwear e-commerce application with two sides:

- Customer storefront for browsing products, managing a Bag, checkout, orders, tracking, and reviews.
- Protected admin area for product management, inventory, order management, and dashboard statistics.

The project is designed as a practical full-stack application that demonstrates REST APIs, database integration, CRUD operations, frontend-backend communication, inventory handling, and order workflows.

## Problem Statement

Many beginner e-commerce projects stop at a static product catalogue and a frontend cart. SoleMate connects the complete shopping flow to a real Spring Boot backend and MySQL database.

The system maintains product data, validates orders against database inventory, stores order items and customer information, and provides administrative tools for managing the catalogue and orders.

## Features

### Customer

- Modern responsive home page
- Dedicated Shop/catalog page
- Product search
- Category filtering
- Price/name sorting
- Product detail pages
- Product image gallery
- Similar-product recommendations
- Product reviews and ratings
- Persistent Bag using browser localStorage
- Quantity management
- Checkout form
- Order creation
- Order confirmation page
- Customer order lookup by email
- Visual order tracking
- Delivery/order status display
- About page

### Admin

- Admin login and logout
- Protected admin dashboard
- Dashboard statistics
- Product search
- Add products
- Edit products
- Delete products
- Inventory/stock management
- Low-stock monitoring
- Order listing
- Order details
- Order status updates

### Backend

- RESTful Spring Boot APIs
- Spring Data JPA persistence
- MySQL integration
- Product CRUD
- Order and OrderItem relationships
- Database-backed product pricing
- Stock validation during checkout
- Automatic stock deduction after successful order creation
- Valid order-status validation
- Customer order lookup
- Review APIs
- Admin dashboard statistics

## Tech Stack

### Backend

- Java 25
- Spring Boot 4.1.1
- Maven
- Spring Web / REST
- Spring Data JPA
- Hibernate
- MySQL

### Frontend

- HTML5
- CSS3
- Vanilla JavaScript
- Fetch API
- Browser localStorage

### Development

- VS Code
- Git
- GitHub
- MySQL Workbench / MySQL CLI

## Architecture

SoleMate follows a layered Spring Boot architecture:

```text
Browser
   |
   | HTTP / JSON
   v
Controllers
   |
   v
Services
   |
   v
Repositories
   |
   v
MySQL Database
```

### Backend package structure

```text
src/main/java/com/solemate/
├── controller/
├── service/
├── repository/
├── entity/
└── SoleMateApplication.java
```

### Frontend structure

```text
src/main/resources/static/
├── index.html
├── shop.html
├── product.html
├── cart.html
├── checkout.html
├── order-success.html
├── my-orders.html
├── track-order.html
├── about.html
├── admin-login.html
├── admin.html
├── css/
├── js/
└── images/
```

## Database Design

### Product

Stores the catalogue information for each shoe.

```text
Product
---------
id
name
brand
price
category
color
availableSizes
stock
imageUrl
imageUrl2
imageUrl3
imageUrl4
imageUrl5
description
featured
```

### Order

Stores customer and order-level information.

```text
Order
---------
id
firstName
lastName
email
phone
address
city
pinCode
totalAmount
status
createdAt
```

### OrderItem

Connects an order to its purchased products.

```text
OrderItem
---------
id
quantity
price
order_id
product_id
```

Relationship:

```text
Order 1 ─────────── * OrderItem * ─────────── 1 Product
```

An order can contain multiple order items, while each order item refers to one product.

## API Endpoints

### Products

| Method | Endpoint | Purpose |
|---|---|---|
| GET | `/api/products` | Get all products |
| GET | `/api/products/{id}` | Get one product |
| POST | `/api/products` | Add a product |
| PUT | `/api/products/{id}` | Update a product |
| DELETE | `/api/products/{id}` | Delete a product |

### Orders

| Method | Endpoint | Purpose |
|---|---|---|
| GET | `/api/orders` | Get all orders |
| GET | `/api/orders/{id}` | Get one order |
| GET | `/api/orders/customer?email=...` | Get orders for a customer |
| POST | `/api/orders` | Create an order |
| PUT | `/api/orders/{id}/status` | Update order status |

Supported order statuses:

```text
PLACED
CONFIRMED
PACKED
OUT_FOR_DELIVERY
DELIVERED
```

### Admin

| Method | Endpoint | Purpose |
|---|---|---|
| GET | `/api/admin/dashboard` | Dashboard statistics |

### Reviews

Review endpoints are used by the product page to submit and retrieve product reviews.

## Order Flow

```text
Customer selects product
        ↓
Add to Bag
        ↓
Checkout
        ↓
POST /api/orders
        ↓
Backend validates product + stock
        ↓
Database price is used
        ↓
Stock is reduced
        ↓
Order + OrderItems are saved
        ↓
Order status = PLACED
        ↓
Order Success
        ↓
Customer can track the order
```

## Validation and Error Handling

The backend validates important product and order data before persistence.

Examples:

- Product name and brand cannot be blank.
- Product price must be greater than zero.
- Stock cannot be negative.
- Requested order quantity must be valid.
- Orders cannot exceed available stock.
- Invalid order statuses are rejected.
- Missing customer email for order lookup is rejected.

## How to Run

### 1. Clone the repository

```bash
git clone https://github.com/ragini-gupta15/SoleMate.git
cd SoleMate
```

### 2. Create the MySQL database

```sql
CREATE DATABASE solemate;
```

### 3. Configure the database

The application uses:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/solemate
spring.datasource.username=root
spring.datasource.password=${DB_PASSWORD}
```

Set `DB_PASSWORD` in your environment before starting the application.

### 4. Start the application

```bash
mvn spring-boot:run
```

The application runs on:

```text
http://localhost:8080
```

## Screenshots

Add final project screenshots here after the UI is captured:

- Home page
- Shop page
- Product details
- Bag
- Checkout
- Order tracking
- Admin dashboard
- Admin product management
- Admin orders

Example:

```text
docs/screenshots/
├── home.png
├── shop.png
├── product.png
├── checkout.png
├── tracking.png
└── admin-dashboard.png
```

## Project Highlights

- 15-product catalogue seeded through the backend
- Database-driven product and order data
- Real inventory deduction during order creation
- Layered Spring Boot architecture
- REST API communication using Fetch API
- Customer and admin workflows
- Responsive storefront and admin interfaces

## Future Improvements

Potential future additions:

- Payment gateway integration
- Customer accounts and authentication
- Wishlist functionality
- Advanced product filtering
- Product variants with per-size inventory
- Email order notifications
- Sales analytics and charts
- Cloud deployment
- Automated tests and CI/CD

## Repository

GitHub:

https://github.com/ragini-gupta15/SoleMate

## Author

Built as a full-stack B.Tech CSE project to demonstrate practical software development, backend engineering, database design, and frontend integration.
