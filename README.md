
---

# 2️⃣ Backend — `README.md`

```markdown
# 💰 Smart Expense Tracker — Backend

A secure and scalable **RESTful backend** for the Smart Expense Tracker application, built using **Java and Spring Boot**.

The backend provides authentication, authorization, financial transaction management, budgeting, reporting, profile management and email notification services.

---

## 🚀 Overview

Smart Expense Tracker is a full-stack personal finance management application.

This repository contains the backend responsible for:

- User authentication
- Authorization
- Expense management
- Income management
- Budget management
- Financial reports
- Dashboard analytics
- Profile management
- Email notifications
- Google OAuth 2.0 authentication
- Database persistence

The backend exposes REST APIs consumed by the React frontend.

---

## ✨ Key Features

### 🔐 Authentication & Security

- JWT-based authentication
- Secure password hashing using BCrypt
- Spring Security
- Google OAuth 2.0 login
- Protected REST endpoints
- Role-based authorization
- JWT token expiration
- CORS configuration
- User-specific data access

### 💸 Expense Management

- Create expenses
- Update expenses
- Delete expenses
- Retrieve expense details
- Search expenses
- Filter expenses
- Category filtering
- Payment method filtering
- Date-range filtering
- Pagination
- Sorting

### 💰 Income Management

- Create income records
- Update income records
- Delete income records
- Retrieve income details
- Search and filtering
- Pagination
- Sorting

### 🎯 Budget Management

- Create monthly budgets
- Category-based budgets
- Prevent duplicate monthly budgets
- Track category spending
- Calculate remaining budget
- Calculate budget utilization percentage

### 📊 Dashboard

Provides financial information such as:

- Current balance
- Total income
- Total expenses
- Remaining budget
- Recent transactions
- Category-wise expenses

### 📈 Reports

The backend provides dedicated APIs for:

#### Monthly Report

Returns:

- Total income
- Total expenses
- Total savings

#### Category-wise Expense Report

Returns expenses grouped by category.

#### Budget Report

Returns:

- Monthly budget
- Amount spent
- Remaining amount
- Percentage used

### 👤 Profile Management

- Retrieve profile
- Update profile
- Change password
- Password validation
- Password change email notification

### 📧 Email

Email functionality includes:

- Password change notification
- SMTP email configuration
- Resend email service integration

---

## 🛠️ Tech Stack

### Backend

- Java 21
- Spring Boot
- Spring MVC
- Spring Security
- Spring Data JPA
- Hibernate
- Jakarta Bean Validation
- REST APIs

### Authentication

- JWT
- Google OAuth 2.0
- BCrypt Password Encoding

### Database

- MySQL
- JPA/Hibernate ORM

### API Documentation

- Swagger
- OpenAPI

### Tools

- Maven
- Git
- GitHub
- Postman
- IntelliJ IDEA
- Docker

### Email

- SMTP
- Resend

---

## 🏗️ Architecture

The application follows a layered architecture:

```text
Client
  │
  ▼
Controller Layer
  │
  ▼
Service Layer
  │
  ▼
Repository Layer
  │
  ▼
MySQL Database
