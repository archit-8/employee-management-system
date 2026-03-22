# Employee Management System

A robust backend application for managing employee data, built with **Spring Boot** and modern Java practices. This system demonstrates clean architecture principles with separation of concerns, dependency injection, and RESTful API design.
  
## ✨ Features

- **Employee Management** - Create, read, update, and delete employee records
- **RESTful API** - Well-designed REST endpoints following HTTP standards
- **Spring Boot** - Lightweight, production-ready Spring application
- **Dependency Injection** - IoC container manages all dependencies
- **Repository Pattern** - Clean data access layer abstraction
- **Exception Handling** - Comprehensive error handling and custom exceptions
- **DTOs** - Data Transfer Objects for API contracts
- **Enums** - Type-safe enum implementations for predefined values
- **Security** - Spring Security integration for authentication and authorization

## 📁 Project Structure

```
employee-management-system/
├── src/main/java/com/example/demo/
│   ├── config/              # Spring configuration classes
│   ├── controller/          # REST API controllers
│   ├── dto/                 # Data Transfer Objects
│   ├── entity/              # JPA entities
│   ├── enums/               # Enum definitions
│   ├── exception/           # Custom exception classes
│   ├── repository/          # Data access layer (Spring Data JPA)
│   ├── service/             # Business logic layer
│   ├── security/            # Security configurations
│   ├── util/                # Utility classes
│   └── EmployeeManagementSystemApplication.java  # Main entry point
├── src/main/resources/
│   ├── application.properties  # Application configuration
│   └── application-dev.properties  # Development profile
├── pom.xml                  # Maven dependencies
└── README.md                # This file
```

## 🔧 Prerequisites

Before running this application, ensure you have the following installed:

- **Java 11+** (JDK)
- **Maven 3.6+** or **Gradle 6.0+**
- **MySQL 5.7+** or **PostgreSQL 10+**
- **Git**

## 📦 Installation & Setup

### 1. Clone the Repository

```bash
git clone https://github.com/yourusername/employee-management-system.git
cd employee-management-system
```

### 2. Configure Database

Create a database for the application:

```sql
CREATE DATABASE employee_management_db;
```

### 3. Update Application Properties

Edit `src/main/resources/application.properties`:

```properties
# Database Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/employee_management_db
spring.datasource.username=root
spring.datasource.password=your_password
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# JPA/Hibernate Configuration
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect

# Server Configuration
server.port=8080
server.servlet.context-path=/api

# Logging
logging.level.root=INFO
logging.level.com.example.demo=DEBUG
```

### 4. Build the Project

```bash
mvn clean install
```

### 5. Run the Application

```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080/api`

## ⚙️ Configuration

### Spring Configuration Files

- **config/** - Contains Spring configuration classes for beans, database, security, etc.

### Application Profiles

Activate different profiles using:

```bash
mvn spring-boot:run -Dspring-boot.run.arguments="--spring.profiles.active=dev"
```

## 🔌 API Endpoints

### Employee Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/employees` | Get all employees |
| `GET` | `/employees/{id}` | Get employee by ID |
| `POST` | `/employees` | Create new employee |
| `PUT` | `/employees/{id}` | Update employee |
| `DELETE` | `/employees/{id}` | Delete employee |
| `GET` | `/employees/search` | Search employees |

### Example Request/Response

**Create Employee (POST)**

```json
{
  "firstName": "John",
  "lastName": "Doe",
  "email": "john.doe@example.com",
  "department": "IT",
  "salary": 50000.00,
  "status": "ACTIVE"
}
```

**Response**

```json
{
  "id": 1,
  "firstName": "John",
  "lastName": "Doe",
  "email": "john.doe@example.com",
  "department": "IT",
  "salary": 50000.00,
  "status": "ACTIVE",
  "createdAt": "2024-01-15T10:30:00Z"
}
```

## 🛠️ Technologies Used

| Technology | Version | Purpose |
|-----------|---------|---------|
| **Spring Boot** | 2.7.x / 3.x | Framework |
| **Spring Data JPA** | Latest | ORM & Data Access |
| **Spring Security** | Latest | Authentication & Authorization |
| **MySQL/PostgreSQL** | 5.7+ / 10+ | Database |
| **Lombok** | Latest | Reduce boilerplate code |
| **MapStruct** | Latest | DTO mapping |
| **JUnit 5** | Latest | Unit testing |
| **Mockito** | Latest | Mocking framework |

## 🏗️ Project Architecture

### Layered Architecture

```
Controller Layer (REST API)
         ↓
Service Layer (Business Logic)
         ↓
Repository Layer (Data Access)
         ↓
Database Layer (MySQL/PostgreSQL)
```

### Key Components

1. **Controller** - Handles HTTP requests and responses
2. **Service** - Contains business logic and orchestrates operations
3. **Repository** - Manages database operations using Spring Data JPA
4. **Entity** - JPA entities representing database tables
5. **DTO** - Data Transfer Objects for API contracts
6. **Exception** - Custom exception handling
7. **Util** - Helper and utility classes

## 🗄️ Database

### Entity Relationship Diagram

Employee Entity with relationships to:
- Department
- Role
- Designation

### Database Schema

```sql
CREATE TABLE employees (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    department VARCHAR(100),
    salary DECIMAL(10, 2),
    status VARCHAR(50),
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);
```

## 🔐 Security

- **Spring Security** - Authentication and authorization
- **JWT Tokens** - Stateless authentication (if implemented)
- **Password Encoding** - BCrypt for secure password storage
- **Role-Based Access Control (RBAC)** - Different user roles with specific permissions

### Enable Security

Configure in `security/` folder:

```java
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    // Security configurations
}
```

## 🧪 Testing

Run tests with Maven:

```bash
mvn test
```

Test coverage report:

```bash
mvn test jacoco:report
```

## 📝 Logging

Logging is configured in `application.properties`. Adjust levels as needed:

```properties
logging.level.com.example.demo=DEBUG
logging.level.org.springframework.web=INFO
logging.file.name=logs/application.log
```

## 🚀 Deployment

### Build JAR File

```bash
mvn clean package
```

### Run JAR File

```bash
java -jar target/employee-management-system-1.0.0.jar
```

### Docker Deployment

Create a `Dockerfile`:

```dockerfile
FROM openjdk:11-jre-slim
COPY target/employee-management-system-1.0.0.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
```

Build and run:

```bash
docker build -t employee-management-system .
docker run -p 8080:8080 employee-management-system
```

## 📚 Documentation

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Spring Data JPA Guide](https://spring.io/projects/spring-data-jpa)
- [Spring Security Reference](https://spring.io/projects/spring-security)

## 🤝 Contributing

Contributions are welcome! Please follow these steps:

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

### Code Style

- Follow Google Java Style Guide
- Use meaningful variable and method names
- Add Javadoc comments for public methods
- Keep methods small and focused


## 📧 Contact

For questions or support, please reach out to:archit442singh@gmail.com

## 🙏 Acknowledgments

- Spring Boot community for excellent documentation
- All contributors who have helped with this project

---

**Happy Coding!** 🚀

Last Updated: March 2024
