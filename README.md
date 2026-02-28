# Todo API - Spring Boot & Supabase

A modern REST API for managing todo tasks, built with Spring Boot and connected to a Supabase PostgreSQL database. This application demonstrates best practices for building scalable, containerized APIs with proper database management and CORS support.

## 📋 Table of Contents

- [Project Overview](#project-overview)
- [Tech Stack](#tech-stack)
- [Architecture](#architecture)
- [Prerequisites](#prerequisites)
- [Installation & Setup](#installation--setup)
- [Supabase Database Configuration](#supabase-database-configuration)
- [Project Structure](#project-structure)
- [API Endpoints](#api-endpoints)
- [Database Schema](#database-schema)
- [Configuration Details](#configuration-details)
- [Running the Application](#running-the-application)
- [Docker Deployment](#docker-deployment)
- [Development Guide](#development-guide)

## 🎯 Project Overview

The Todo API is a RESTful web service that allows users to create, read, and manage todo tasks. The application is fully integrated with **Supabase**, a modern backend-as-a-service platform built on PostgreSQL, enabling reliable data persistence and real-time capabilities.

### Key Features
- ✅ Create, read, and update todo tasks
- ✅ Mark tasks as done/undone
- ✅ CORS-enabled for cross-origin requests
- ✅ PostgreSQL database via Supabase
- ✅ Docker containerization support
- ✅ Spring Boot actuator for monitoring
- ✅ Automatic timestamp management
- ✅ Java 21 compatibility

## 🛠️ Tech Stack

| Component | Technology | Version |
|-----------|-----------|---------|
| **Framework** | Spring Boot | 4.0.3 |
| **Language** | Java | 21 |
| **Database** | PostgreSQL via Supabase | Latest |
| **ORM** | Spring Data JPA | Included in Spring Boot |
| **Build Tool** | Maven | 3.9.8+ |
| **Container** | Docker | Latest |
| **Additional** | Lombok | For code generation |

## 🏗️ Architecture

```
┌─────────────────────────────────────────────────────────────┐
│                     Spring Boot Application                  │
│                                                               │
│  ┌─────────────────────────────────────────────────────┐   │
│  │          TodoController (@RestController)           │   │
│  │  Handles HTTP requests/responses for /todo/api/v1/* │   │
│  └──────────────────────┬──────────────────────────────┘   │
│                         │                                     │
│  ┌──────────────────────▼──────────────────────────────┐   │
│  │   TodoRepository (Spring Data JPA)                  │   │
│  │   Provides database access methods                  │   │
│  └──────────────────────┬──────────────────────────────┘   │
│                         │                                     │
│  ┌──────────────────────▼──────────────────────────────┐   │
│  │   Todo Entity (@Entity)                             │   │
│  │   Mapped to PostgreSQL table: todo_table            │   │
│  └──────────────────────┬──────────────────────────────┘   │
└─────────────────────────┼──────────────────────────────────┘
                          │
┌─────────────────────────▼──────────────────────────────────┐
│                  Supabase PostgreSQL                        │
│  ┌──────────────────────────────────────────────────┐      │
│  │  Database: postgres                              │      │
│  │  Table: todo_table                               │      │
│  │  Region: ap-northeast-2 (Seoul)                  │      │
│  └──────────────────────────────────────────────────┘      │
└────────────────────────────────────────────────────────────┘
```

## 📋 Prerequisites

Before you begin, ensure you have the following installed:

- **Java JDK 21** or higher
- **Maven 3.9.0** or higher
- **PostgreSQL** (local) OR **Supabase Account** (recommended)
- **Docker** (optional, for containerized deployment)
- **Git** (for version control)

### Supabase Account Setup
If you don't have a Supabase account:
1. Visit [supabase.com](https://supabase.com)
2. Click "Start your project"
3. Sign up with your email or GitHub account
4. Create a new project and organization
5. Note your **Project URL** and **Database password**

## 🚀 Installation & Setup

### 1. Clone the Repository
```bash
git clone https://github.com/SonJaeMark/todo-api.git
cd todo-api
```

### 2. Create Environment Configuration

Create a `.env` file or set environment variables:
```bash
# Database Connection (Supabase)
export DB_URL="jdbc:postgresql://[project-id].pooler.supabase.com:5432/postgres?sslmode=require"
export DB_USER="postgres.[your-db-user]"
export DB_PASSWORD="[your-secure-password]"

# Server Configuration
export PORT=8080
```

**For Windows (PowerShell):**
```powershell
$env:DB_URL = "jdbc:postgresql://[project-id].pooler.supabase.com:5432/postgres?sslmode=require"
$env:DB_USER = "postgres.[your-db-user]"
$env:DB_PASSWORD = "[your-secure-password]"
$env:PORT = "8080"
```

### 3. Build the Application
```bash
mvn clean install
```

### 4. Run the Application
```bash
mvn spring-boot:run
```

Or run the JAR directly:
```bash
java -jar target/todo-api-0.0.1-SNAPSHOT.jar
```

The API will be available at: `http://localhost:8080`

## 🗄️ Supabase Database Configuration

### Understanding the Supabase Connection

The application connects to Supabase using JDBC (Java Database Connectivity) through PostgreSQL drivers. The connection is configured in `application.yaml` with the following key components:

```yaml
spring:
  datasource:
    # Supabase provides a PostgreSQL database with pooler connection
    url: jdbc:postgresql://aws-1-ap-northeast-2.pooler.supabase.com:5432/postgres?sslmode=require
    
    # Your Supabase database credentials
    username: postgres.rszinluakxykjxcioiax
    password: ${DB_PASSWORD}
    
    # PostgreSQL JDBC driver
    driver-class-name: org.postgresql.Driver
```

### Connection String Breakdown

```
jdbc:postgresql://[HOST]:[PORT]/[DATABASE]?sslmode=require
                 │               │    │
                 │               │    └─ Default database name
                 │               └────── Port (usually 5432)
                 └───────────────────── Supabase pooler endpoint
```

**Supabase Connection Components:**
- **Host**: `aws-1-ap-northeast-2.pooler.supabase.com` (Varies by region)
- **Port**: `5432` (Standard PostgreSQL)
- **Database**: `postgres` (Default Supabase database)
- **SSL**: `sslmode=require` (Required for security)
- **Username**: `postgres.[your-project-slug]`

### Getting Your Supabase Connection String

1. Go to your **Supabase Project Dashboard**
2. Click **Settings** → **Database**
3. Copy the connection string under **Connection String** section
4. Replace environment variables in `application.yaml`

### Why Supabase?

✅ **Managed PostgreSQL** - No server management overhead  
✅ **SSL Encrypted** - Secure connections by default  
✅ **Connection Pooling** - Built-in query optimization  
✅ **Real-time Subscriptions** - WebSocket support (future enhancement)  
✅ **Scalability** - Auto-scaling capabilities  
✅ **REST API** - Additional API layer if needed  

## 📁 Project Structure

```
todo-api/
│
├── src/
│   ├── main/
│   │   ├── java/com/github/sonjaemark/todo_api/
│   │   │   ├── TodoApiApplication.java          ← Spring Boot entry point
│   │   │   ├── controller/
│   │   │   │   └── TodoController.java          ← REST endpoints
│   │   │   ├── entity/
│   │   │   │   └── Todo.java                    ← JPA entity (database model)
│   │   │   └── repository/
│   │   │       └── TodoRepository.java          ← Data access layer
│   │   └── resources/
│   │       └── application.yaml                 ← Configuration file
│   │
│   └── test/
│       └── java/.../TodoApiApplicationTests.java
│
├── pom.xml                                      ← Maven dependency configuration
├── Dockerfile                                   ← Docker build configuration
├── mvnw / mvnw.cmd                             ← Maven wrapper scripts
└── README.md                                    ← This file
```

## 📡 API Endpoints

### Base URL
```
http://localhost:8080/todo/api/v1
```

### 1. Health Check
**GET** `/`
- **Description**: Verify API is running
- **Response**: Simple string confirmation
- **Status Code**: 200 OK

**Example:**
```bash
curl http://localhost:8080/todo/api/v1/
```

**Response:**
```
API is working!
```

---

### 2. Create Task
**POST** `/create-task`
- **Description**: Create a new todo task
- **Content-Type**: `application/json`
- **Request Body**:
  ```json
  {
    "task": "Complete documentation"
  }
  ```
- **Status Code**: 201 Created
- **Response**: Created Todo object with ID, timestamps, and status

**Example:**
```bash
curl -X POST http://localhost:8080/todo/api/v1/create-task \
  -H "Content-Type: application/json" \
  -d '{"task":"Buy groceries"}'
```

**Response:**
```json
{
  "id": 1,
  "task": "Buy groceries",
  "is_done": false,
  "created_at": "2026-02-28T14:30:45"
}
```

**Error Cases**:
- `400 Bad Request`: If task field is missing or empty
- `500 Internal Server Error`: Database connection issues

---

### 3. Get All Tasks
**GET** `/get-all-task`
- **Description**: Retrieve all tasks from the database
- **Status Code**: 200 OK
- **Response**: Array of Todo objects

**Example:**
```bash
curl http://localhost:8080/todo/api/v1/get-all-task
```

**Response:**
```json
[
  {
    "id": 1,
    "task": "Buy groceries",
    "is_done": false,
    "created_at": "2026-02-28T14:30:45"
  },
  {
    "id": 2,
    "task": "Complete project",
    "is_done": true,
    "created_at": "2026-02-27T10:15:30"
  }
]
```

---

### 4. Mark Task as Done
**PUT** `/mark-as-done/{id}`
- **Description**: Mark a specific task as completed
- **Path Parameter**: `id` (Long) - Task ID
- **Status Code**: 200 OK
- **Response**: Updated Todo object with `is_done: true`

**Example:**
```bash
curl -X PUT http://localhost:8080/todo/api/v1/mark-as-done/1
```

**Response:**
```json
{
  "id": 1,
  "task": "Buy groceries",
  "is_done": true,
  "created_at": "2026-02-28T14:30:45"
}
```

**Error Cases**:
- `404 Not Found`: If task ID doesn't exist
- `500 Internal Server Error`: Database errors

---

### 5. Update Task
**PUT** `/update-task/{id}`
- **Description**: Update the text of an existing task
- **Path Parameter**: `id` (Long) - Task ID
- **Content-Type**: `application/json`
- **Request Body**:
  ```json
  {
    "task": "Updated task description"
  }
  ```
- **Status Code**: 200 OK
- **Response**: Updated Todo object with new task text

**Example:**
```bash
curl -X PUT http://localhost:8080/todo/api/v1/update-task/1 \
  -H "Content-Type: application/json" \
  -d '{"task":"Buy organic groceries"}'
```

**Response:**
```json
{
  "id": 1,
  "task": "Buy organic groceries",
  "is_done": false,
  "created_at": "2026-02-28T14:30:45"
}
```

**Error Cases**:
- `404 Not Found`: If task ID doesn't exist
- `400 Bad Request`: If task field is invalid

---

## 🗂️ Database Schema

### Table: `todo_table`

| Column | Type | Constraints | Description |
|--------|------|-------------|-------------|
| `id` | BIGINT | PRIMARY KEY, AUTO_INCREMENT | Unique identifier for each task |
| `task` | VARCHAR(255) | NOT NULL | Task description text |
| `is_done` | BOOLEAN | DEFAULT FALSE | Task completion status |
| `created_at` | TIMESTAMP | DEFAULT CURRENT_TIMESTAMP | Task creation timestamp |

### SQL Create Statement
```sql
CREATE TABLE todo_table (
  id BIGSERIAL PRIMARY KEY,
  task VARCHAR(255) NOT NULL,
  is_done BOOLEAN DEFAULT false,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Optional: Create an index for faster queries
CREATE INDEX idx_todo_is_done ON todo_table(is_done);
```

### How to Create the Table in Supabase

1. **Via Supabase Dashboard**:
   - Go to **SQL Editor**
   - Click **New Query**
   - Paste the SQL above and execute

2. **Via pgAdmin** (if desired):
   - Connect using your Supabase credentials
   - Create the table using the SQL statement

3. **Automatic Creation** (NOT recommended for production):
   - Temporarily set `hibernate.ddl-auto: create` in `application.yaml`
   - Run the application once
   - Revert to `none` for production use

## ⚙️ Configuration Details

### Application Configuration (`application.yaml`)

```yaml
spring:
  datasource:
    # Supabase PostgreSQL connection
    url: ${DB_URL:jdbc:postgresql://aws-1-ap-northeast-2.pooler.supabase.com:5432/postgres?sslmode=require}
    username: ${DB_USER:postgres.rszinluakxykjxcioiax}
    password: ${DB_PASSWORD}  # Must be provided at runtime
    driver-class-name: org.postgresql.Driver
  
  jpa:
    # Hibernate configuration for JPA
    hibernate:
      ddl-auto: none  # Never auto-modify schema in production
    show-sql: true    # Log SQL queries (disable in production)
    properties:
      hibernate:
        dialect: org.hibernate.dialect.PostgreSQLDialect
        '[format_sql]': true  # Pretty-print SQL queries

server:
  port: ${PORT:8080}  # Application port (customizable)
```

### Environment Variables

| Variable | Description | Example |
|----------|-------------|---------|
| `DB_URL` | Supabase PostgreSQL connection string | `jdbc:postgresql://...` |
| `DB_USER` | Database username | `postgres.yourproject` |
| `DB_PASSWORD` | Database password | Your secure password |
| `PORT` | Server port | `8080` |

### Dependency Management (`pom.xml`)

**Key Dependencies:**
- **spring-boot-starter-webmvc**: REST API support
- **spring-boot-starter-data-jpa**: ORM and database abstraction
- **spring-boot-starter-actuator**: Health checks and monitoring
- **postgresql**: JDBC driver for PostgreSQL/Supabase
- **lombok**: Annotation processing to reduce boilerplate

## ▶️ Running the Application

### Development Mode
```bash
# Using Maven
mvn spring-boot:run

# Or build and run JAR
mvn clean package
java -jar target/todo-api-0.0.1-SNAPSHOT.jar
```

### Production Mode
Set environment variables before running:
```bash
export DB_PASSWORD="your-secure-password"
java -jar target/todo-api-0.0.1-SNAPSHOT.jar
```

### Health Check
```bash
curl http://localhost:8080/actuator
```

## 🐳 Docker Deployment

The application includes a optimized **multi-stage Dockerfile** for containerization.

### Build Docker Image
```bash
docker build -t todo-api:latest .
```

### Run Docker Container
```bash
docker run -e DB_PASSWORD="your-password" \
           -e DB_URL="jdbc:postgresql://..." \
           -e DB_USER="postgres.yourproject" \
           -p 8080:8080 \
           todo-api:latest
```

### Docker Compose (Optional)
Create `docker-compose.yaml`:
```yaml
version: '3.8'
services:
  todo-api:
    build: .
    ports:
      - "8080:8080"
    environment:
      DB_URL: jdbc:postgresql://aws-1-ap-northeast-2.pooler.supabase.com:5432/postgres?sslmode=require
      DB_USER: postgres.yourproject
      DB_PASSWORD: ${DB_PASSWORD}
      PORT: 8080
```

Run with Docker Compose:
```bash
docker-compose up
```

### Dockerfile Explanation

```dockerfile
# Stage 1: Build
FROM maven:3.9.8-eclipse-temurin-21 AS builder
# Compile application using Maven

# Stage 2: Runtime
FROM eclipse-temurin:21-jre-alpine
# Run compiled JAR with minimal footprint
```

**Benefits:**
- ✅ Smaller final image size (~200MB vs 500MB+)
- ✅ Only production runtime needed
- ✅ Security: No build tools in production image
- ✅ Alpine Linux: Lightweight base image

## 👨💻 Development Guide

### Project Structure Explanation

#### 1. **TodoApiApplication.java** - Entry Point
Marks the application as a Spring Boot application. The `@SpringBootApplication` annotation combines three essential annotations:
- `@Configuration`: Defines configuration class
- `@ComponentScan`: Scans for beans
- `@EnableAutoConfiguration`: Auto-configures Spring based on classpath

#### 2. **TodoController.java** - REST Endpoints
Handles HTTP requests and responses. Key annotations:
- `@RestController`: Combines `@Controller` + `@ResponseBody`
- `@RequestMapping("/todo/api/v1")`: Base URL for all endpoints
- `@CrossOrigin(origins = "*")`: Enables CORS for all origins
- `@RequiredArgsConstructor`: Lombok annotation for dependency injection

#### 3. **Todo.java** - JPA Entity
Defines the database model with Lombok annotations:
- `@Entity`: Marks class as JPA entity
- `@Table(name = "todo_table")`: Maps to database table
- `@PrePersist`: Automatically sets defaults before saving
- `@Getter/@Setter`: Auto-generates getters/setters

#### 4. **TodoRepository.java** - Data Access Layer
Extends `JpaRepository` to provide CRUD operations:
- Automatically implements: `save()`, `findById()`, `findAll()`, `delete()`
- No need to write SQL queries
- Integrates with Supabase PostgreSQL

### Testing

Run tests with Maven:
```bash
mvn test
```

The included `TodoApiApplicationTests.java` validates the Spring Boot context loads correctly.

### Building for Production

1. **Clean build**:
   ```bash
   mvn clean package -DskipTests
   ```

2. **Verify JAR**:
   ```bash
   jar tf target/todo-api-0.0.1-SNAPSHOT.jar | head -20
   ```

3. **Run JAR**:
   ```bash
   java -jar target/todo-api-0.0.1-SNAPSHOT.jar
   ```

### Logging

View logs in development:
```
SET SPRING_JPA_PROPERTIES_HIBERNATE_FORMAT_SQL=true
SET SHOW_SQL=true
```

SQL queries will print to console in formatted manner.

## 🤝 Contributing

Contributions are welcome! Please follow these steps:

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit changes (`git commit -m 'Add amazing feature'`)
4. Push to branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## 📝 License

This project is open source and available under the MIT License.

## 🆘 Troubleshooting

### Common Issues

**1. Database Connection Failed**
```
Error: FATAL: database "postgres" does not exist
```
- Verify Supabase project is active
- Check connection string in `application.yaml`
- Verify environment variables are set correctly
- Ensure SSL mode is set to `require`

**2. Table Not Found**
```
Error: relation "todo_table" does not exist
```
- Create the table using SQL in Supabase dashboard
- Verify `hibernation.ddl-auto` is set to `none` in production

**3. CORS Origin Not Allowed**
```
Access to XMLHttpRequest blocked by CORS policy
```
- Modify `@CrossOrigin(origins = "your-frontend-url")` in TodoController
- Or keep `@CrossOrigin(origins = "*")` for development only

**4. Port Already in Use**
```
Port 8080 is already in use
```
Change port in `application.yaml` or environment:
```bash
export PORT=9090
```

## 📞 Support

For issues or questions:
- Create an issue on GitHub
- Check existing documentation
- Review Supabase docs: https://supabase.com/docs

---

**Made with ❤️ using Spring Boot and Supabase**
