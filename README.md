# FinSight Analytics API

A robust Spring Boot backend application designed to provide meaningful
financial insights through optimized analytics on income and expense data.
The project focuses on clean architecture, efficient data aggregation,
and real-world backend engineering practices.

---

## 🚀 Key Highlights

- Built with an **analytics-first approach**, not just CRUD
- Uses **database-level aggregation** for performance and scalability
- Clean separation of Controller, Service, Repository, and DTO layers
- Production-ready exception handling and validation
- Fully documented REST APIs using OpenAPI (Swagger)
- Unit-tested service layer ensuring correctness and reliability

---

## ✨ Core Features

### 📊 Financial Analytics
- Monthly income vs expense summary
- Monthly balance calculation
- Category-wise income analytics
- Category-wise expense analytics

### ⚡ Performance-Oriented Design
- JPQL aggregation queries (`SUM`, `CASE`, `GROUP BY`)
- Reduced in-memory processing
- Single-query monthly analytics for optimized data access

### 🧱 Clean Architecture
- DTO-based request and response models
- Centralized global exception handling
- Validation using Jakarta Bean Validation

### 🧪 Testing & Quality
- Service-layer unit tests using JUnit 5 and Mockito
- Business logic tested independently of controllers
- Build verified using Maven (`mvn test`)

---

## 🛠️ Tech Stack

- **Java 21**
- **Spring Boot**
- **Spring Data JPA**
- **Hibernate**
- **Maven**
- **JUnit 5 & Mockito**
- **OpenAPI (Swagger)**
- **MySQL / H2 (configurable)**

---

## 📡 REST API Overview

| Method | Endpoint | Description |
|------|---------|-------------|
| POST | `/api/transactions` | Create income or expense transaction |
| GET | `/api/transactions/analytics/category-expense` | Category-wise expenses for a month |
| GET | `/api/transactions/analytics/category-income` | Category-wise income for a month |
| GET | `/api/transactions/analytics/monthly-summary` | Monthly income vs expense |
| GET | `/api/transactions/analytics/monthly-balance` | Monthly balance |

---

## 🧪 Running Tests

```bash

mvn test
```
## ▶️ Running the Application
```bash

mvn clean install
mvn spring-boot:run
```

## 📖 API Documentation (Swagger)

- Once the application is running, access: http://localhost:8080/swagger-ui.html

## 🎯 Design Philosophy

- **This project emphasizes:**

- **Writing efficient backend logic**

- **Leveraging the database for analytics**

- **Building maintainable and testable services**

- **Following real-world backend engineering standards**

## 🔮 Future Enhancements

- **User authentication & authorization (JWT)**

- **Multi-user finance tracking**

- **Budget limits and alerts**

- **Data visualization support**

- **Containerization & CI/CD**
---

## 👤 Author
**Sandhya Sara**
**| Java Backend Developer**

