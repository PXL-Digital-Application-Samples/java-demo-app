# 🧩 Spring Boot User Management API

A lightweight Java Spring Boot-based REST API for managing users (Create, Read, Update, Delete) with in-memory data storage. Includes auto-seeded user data and interactive Swagger (OpenAPI) documentation served at the root (`/`).

---

## 📦 Features

- ✅ In-memory storage for rapid development and testing  
- 🔄 Full CRUD endpoints: `POST`, `GET`, `PUT`, `DELETE`  
- 🧪 Swagger UI for testing & docs at `/` 
- 🚀 Minimal setup, ready for DevOps CI/CD pipelines  
- 🧰 Easily extendable to use persistent databases (e.g., H2, PostgreSQL)
- 📋 Input validation with Bean Validation
- 🔧 Built with Spring Boot 3.2.0 and Java 17

---

## 📋 Getting Started

### Requirements

- Java 17+
- Maven 3.6+

### Installation

```bash
# Clone the repository
git clone <repository-url>
cd user-management-api

# Install dependencies
mvn clean install
```

### Run the Application

```bash
# Using Maven
mvn spring-boot:run

# Or build and run the JAR
mvn clean package
java -jar target/user-management-api-1.0.0.jar

# With custom port (default is 5000)
java -jar target/user-management-api-1.0.0.jar --server.port=8080
```

### Run Tests

```bash
# Run all tests
mvn test

# Run tests with coverage report
mvn test jacoco:report

# Generate test reports in XML format (for CI/CD)
mvn surefire-report:report
```

---

## 🔌 API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/users` | Create a new user |
| GET | `/users` | Get all users |
| GET | `/users/{id}` | Get a specific user |
| PUT | `/users/{id}` | Update a user |
| DELETE | `/users/{id}` | Delete a user |

### Example Requests

**Create User:**
```bash
curl -X POST http://localhost:5000/users \
  -H "Content-Type: application/json" \
  -d '{"name": "John Doe", "email": "john@example.com"}'
```

**Get All Users:**
```bash
curl http://localhost:5000/users
```

**Update User:**
```bash
curl -X PUT http://localhost:5000/users/1 \
  -H "Content-Type: application/json" \
  -d '{"name": "Jane Doe"}'
```

---

## 📚 Swagger Documentation

Once the application is running, navigate to:
- Swagger UI: `http://localhost:5000/`
- API Docs (JSON): `http://localhost:5000/api-docs`

---

## 🏗️ Project Structure

```
user-management-api/
├── src/
│   ├── main/
│   │   ├── java/com/example/usermanagement/
│   │   │   ├── UserManagementApplication.java
│   │   │   ├── controller/
│   │   │   │   └── UserController.java
│   │   │   ├── model/
│   │   │   │   └── User.java
│   │   │   └── service/
│   │   │       └── UserService.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/
│       └── java/com/example/usermanagement/
│           └── controller/
│               └── UserControllerTest.java
├── pom.xml
└── README.md
```

---

## 🔧 Configuration

Key configuration options in `application.properties`:

```properties
# Server port (default: 5000)
server.port=${PORT:5000}

# Swagger UI path (default: /)
springdoc.swagger-ui.path=/

# Logging level
logging.level.com.example.usermanagement=DEBUG
```

---
