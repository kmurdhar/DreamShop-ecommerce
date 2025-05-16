# 🛍️ DreamShop E-commerce API

DreamShop is a Java Spring Boot-based backend project for an e-commerce platform. It provides APIs for managing products, user registration, carts, and order placements — designed with scalability and clean architecture in mind.

---

## 🚀 Features

- ✅ Product catalog API
- 🛒 Add to Cart & manage cart items
- 📦 Place Orders and track status
- 👤 User registration and authentication
- 🔒 JWT-based security (if implemented)
- 📊 Future-ready for analytics and dashboard integration

---

## 🧱 Tech Stack

- **Backend**: Java 8+, Spring Boot, Spring MVC, Spring Data JPA
- **Database**: MySQL / PostgreSQL
- **Build Tool**: Maven
- **Security**: (Optional) Spring Security + JWT
- **Frontend**: (Planned or integrated via API)
- **API Docs**: Swagger (recommended)

---

## 📁 Project Structure (Module: dream-shops-third-commit-with-modifications)

src/
├── main/
│ ├── java/com/dreamshop/
│ │ ├── controller/
│ │ ├── model/
│ │ ├── service/
│ │ ├── repository/
│ │ └── DreamShopApplication.java
│ └── resources/
│ ├── application.properties
│ └── data.sql / schema.sql
└── test/


---

## 🔧 Setup Instructions

### Prerequisites
- Java 17+ (or Java 8 if legacy)
- Maven 3.6+
- MySQL or PostgreSQL running
- IDE (IntelliJ or VS Code)

### Steps to Run Locally

```bash
# Clone the repo
git clone https://github.com/kmurdhar/DreamShop-ecommerce.git
cd DreamShop-ecommerce/dream-shops-third-commit-with-modifications

# Build project
mvn clean install

# Run the Spring Boot app
mvn spring-boot:run

| Method | Endpoint           | Description         |
| ------ | ------------------ | ------------------- |
| GET    | `/products`        | Get all products    |
| POST   | `/cart/add`        | Add product to cart |
| GET    | `/cart/{userId}`   | Get user’s cart     |
| POST   | `/order/place`     | Place an order      |
| GET    | `/orders/{userId}` | Get user orders     |
