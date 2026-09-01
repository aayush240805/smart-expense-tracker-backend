
---

# 2️⃣ Backend — `README.md`

# 💰 Smart Expense Tracker — Backend

A secure and scalable **RESTful backend** for the Smart Expense Tracker application, built using **Java and Spring Boot**.

The backend provides authentication, authorization, financial transaction management, budgeting, reporting, profile management and email notification services.


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


## 🏗️ Architecture

The application follows a layered architecture:

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

## 🔐 Security Flow 

Client
  │
  ▼
Spring Security
  │
  ├── JWT Authentication
  │
  └── Google OAuth 2.0
          │
          ▼
     Authenticated User
          │
          ▼
      Controllers


## 📂 Project Structure

smart-expense-tracker-backend/
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── expensetracker/
│   │   │
│   │   │       ├── SmartExpenseTrackerApplication.java
│   │   │       │
│   │   │       ├── config/
│   │   │       │   ├── CorsConfig.java
│   │   │       │   ├── DataInitializer.java
│   │   │       │   ├── OpenApiConfig.java
│   │   │       │   ├── ResendConfig.java
│   │   │       │   └── SecurityConfig.java
│   │   │       │
│   │   │       ├── controller/
│   │   │       │   ├── AuthController.java
│   │   │       │   ├── BudgetController.java
│   │   │       │   ├── CategoryController.java
│   │   │       │   ├── DashboardController.java
│   │   │       │   ├── ExpenseController.java
│   │   │       │   ├── IncomeController.java
│   │   │       │   ├── ProfileController.java
│   │   │       │   └── ReportController.java
│   │   │       │
│   │   │       ├── dto/
│   │   │       │   ├── request/
│   │   │       │   │   ├── BudgetRequest.java
│   │   │       │   │   ├── ExpenseRequest.java
│   │   │       │   │   ├── IncomeRequest.java
│   │   │       │   │   ├── LoginRequest.java
│   │   │       │   │   └── RegisterRequest.java
│   │   │       │   │
│   │   │       │   ├── response/
│   │   │       │   │   ├── ApiResponse.java
│   │   │       │   │   ├── BudgetResponse.java
│   │   │       │   │   ├── CategoryResponse.java
│   │   │       │   │   ├── DashboardResponse.java
│   │   │       │   │   ├── ExpenseResponse.java
│   │   │       │   │   ├── IncomeResponse.java
│   │   │       │   │   ├── LoginResponse.java
│   │   │       │   │   ├── MonthlyReportEmailResponse.java
│   │   │       │   │   ├── PageResponse.java
│   │   │       │   │   └── RecentTransactionResponse.java
│   │   │       │   │
│   │   │       │   ├── otpRequest/
│   │   │       │   │   ├── ForgotPasswordRequest.java
│   │   │       │   │   └── ResetPasswordRequest.java
│   │   │       │   │
│   │   │       │   ├── profileResponse/
│   │   │       │   │   ├── ChangePasswordRequest.java
│   │   │       │   │   ├── ProfileResponse.java
│   │   │       │   │   └── UpdateProfileRequest.java
│   │   │       │   │
│   │   │       │   └── reportResponse/
│   │   │       │       ├── BudgetReportResponse.java
│   │   │       │       ├── CategoryExpenseResponse.java
│   │   │       │       └── MonthlyReportResponse.java
│   │   │       │
│   │   │       ├── entity/
│   │   │       │   ├── BaseEntity.java
│   │   │       │   ├── User.java
│   │   │       │   ├── Expense.java
│   │   │       │   ├── Income.java
│   │   │       │   ├── Budget.java
│   │   │       │   ├── Category.java
│   │   │       │   └── Otp.java
│   │   │       │
│   │   │       ├── enums/
│   │   │       │   ├── AuthProvider.java
│   │   │       │   ├── CategoryType.java
│   │   │       │   ├── PaymentMethod.java
│   │   │       │   └── Role.java
│   │   │       │
│   │   │       ├── exception/
│   │   │       │   ├── BadRequestException.java
│   │   │       │   ├── DuplicateResourceException.java
│   │   │       │   ├── ErrorResponse.java
│   │   │       │   ├── GlobalExceptionHandler.java
│   │   │       │   └── ResourceNotFoundException.java
│   │   │       │
│   │   │       ├── repository/
│   │   │       │   ├── UserRepository.java
│   │   │       │   ├── ExpenseRepository.java
│   │   │       │   ├── IncomeRepository.java
│   │   │       │   ├── BudgetRepository.java
│   │   │       │   ├── CategoryRepository.java
│   │   │       │   └── OtpRepository.java
│   │   │       │
│   │   │       ├── scheduler/
│   │   │       │   └── MonthlyReportEmailScheduler.java
│   │   │       │
│   │   │       ├── security/
│   │   │       │   ├── CustomUserDetailsService.java
│   │   │       │   ├── JwtAuthenticationEntryPoint.java
│   │   │       │   ├── JwtAuthenticationFilter.java
│   │   │       │   ├── JwtService.java
│   │   │       │   ├── UserPrincipal.java
│   │   │       │   │
│   │   │       │   └── OAuth2/
│   │   │       │       ├── CustomOidcUserPrincipal.java
│   │   │       │       ├── CustomOidcUserService.java
│   │   │       │       ├── OAuth2AuthenticationFailureHandler.java
│   │   │       │       └── OAuth2AuthenticationSuccessHandler.java
│   │   │       │
│   │   │       ├── service/
│   │   │       │   ├── AuthService.java
│   │   │       │   ├── BudgetService.java
│   │   │       │   ├── CategoryService.java
│   │   │       │   ├── DashboardService.java
│   │   │       │   ├── EmailService.java
│   │   │       │   ├── ExpenseService.java
│   │   │       │   ├── IncomeService.java
│   │   │       │   ├── MonthlyReportEmailService.java
│   │   │       │   ├── OtpService.java
│   │   │       │   ├── ProfileService.java
│   │   │       │   ├── ReportService.java
│   │   │       │   │
│   │   │       │   └── impl/
│   │   │       │       ├── AuthServiceImpl.java
│   │   │       │       ├── BudgetServiceImpl.java
│   │   │       │       ├── CategoryServiceImpl.java
│   │   │       │       ├── DashboardServiceImpl.java
│   │   │       │       ├── EmailServiceImpl.java
│   │   │       │       ├── ExpenseServiceImpl.java
│   │   │       │       ├── IncomeServiceImpl.java
│   │   │       │       ├── MonthlyReportEmailService.java
│   │   │       │       ├── OtpServiceImpl.java
│   │   │       │       ├── ProfileServiceImpl.java
│   │   │       │       └── ReportServiceImpl.java
│   │   │       │
│   │   │       ├── specification/
│   │   │       │   ├── BudgetSpecification.java
│   │   │       │   ├── ExpenseSpecification.java
│   │   │       │   └── IncomeSpecification.java
│   │   │       │
│   │   │       ├── util/
│   │   │       │   ├── CurrentUserService.java
│   │   │       │   └── PaginationUtil.java
│   │   │       │
│   │   │       └── validation/
│   │   │           ├── ValidationGroups.java
│   │   │           └── ValidationSequence.java
│   │   │
│   │   └── resources/
│   │       └── application.yml
│   │
│   └── test/
│       └── java/
│           └── com/
│               └── expensetracker/
│                   └── SmartExpenseTrackerApplicationTests.java
│
├── .mvn/
│   └── wrapper/
│       └── maven-wrapper.properties
│
├── .dockerignore
├── .gitattributes
├── .gitignore
├── Dockerfile
├── mvnw
├── mvnw.cmd
├── pom.xml
└── README.md
