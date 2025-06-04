# 📚 library-T3 – Library Management System in Java

A console-based application to manage books, authors, and genres in a virtual library. This project uses **Java 21**, **MySQL**, and follows clean and modular design principles. It also includes testing with **JUnit 5** and environment variable management for database configuration.

---

## 📑 Table of Contents

- [Project Screenshots](#-project-screenshots)
- [Features](#-features)
- [Architecture](#-architecture)
- [Technologies Used](#-technologies-used)
- [How to Run](#-how-to-run)
- [Usage](#-usage)
- [Tests](#-tests)
- [Console Interface Features](#-console-interface-features)
- [Project Structure](#-project-structure)
- [Error Handling](#-error-handling)
- [Future Enhancements](#-future-enhancements)
- [Acknowledgments](#-acknowledgments)
- [Support](#-support)

- [Team](#-team)

---

## 📸 Project Screenshots

[![image.png](https://i.postimg.cc/Y23D0Gh9/image.png)](https://postimg.cc/F1YVnKb5)

---

## 🎯 Features

- Complete Book Management: Add, view, update, and delete books
- Multi-Author Support: Books can have multiple authors
- Genre Classification: Categorize books by multiple genres
- Advanced Search: Search books by title, author, or genre
- Database Integration: MySQL database with automatic schema initialization
- Input Validation: Comprehensive validation for all user inputs
- Error Handling: Robust error handling with user-friendly messages
- Colorized Interface: Enhanced console UI with colors and emojis
- Unit Testing: Comprehensive test coverage with JUnit 5 and Mockito

---

## 🏗️ Architecture

The application follows a layered architecture pattern:

```bash

├── Model Layer (org.LT3.model)
│   ├── Book.java
│   ├── Author.java
│   └── Genre.java
├── Repository Layer (org.LT3.repository)
│   ├── BookRepository.java (Interface)
│   └── MySqlBookRepository.java (Implementation)
├── Controller Layer (org.LT3.controller)
│   └── BookController.java
├── View Layer (org.LT3.view)
│   └── BookView.java
├── Configuration (org.LT3.config)
│   ├── DatabaseConnection.java
│   └── DatabaseInitializer.java
└── Utilities (org.LT3.util)
    └── ColorConstants.java

```

---

## 🛠️ Technologies Used

- Java 21: Modern Java features and syntax
- Maven: Dependency management and build automation
- MySQL: Relational database management
- JUnit 5: Unit testing framework
- Mockito: Mocking framework for testing
- Dotenv Java: Environment variable management
- JDBC: Database connectivity

---

## 🚀 How to Run

### Requirements

Before running the application, ensure you have:

- Java Development Kit (JDK) 21 or higher
- Apache Maven 3.6+
- MySQL Server 8.0+ running locally or remotely
- Git (for cloning the repository)
- A `.env` file with your database credentials

### 1. Clone the repository

```bash

https://github.com/Library-Team-3/library-T3.git
cd library-T3
```

### 2. Create a .env file

```env
DB_HOST=localhost
DB_PORT=3306
DB_NAME=library
DB_USER=your_username
DB_PASSWORD=your_password
```

### 3. Initialize the database

Execute the contents of the schema.sql file in your **MySQL** database to create the necessary tables and insert initial data.

### 4. Run the application from terminal

```bash

mvn clean compile
mvn exec:java -Dexec.mainClass="com.library.Main"

```

---

## 🎮 Usage

### 📚 Show All Books

- Displays all books in the library with their details
- Shows book ID, title, description, ISBN, authors, and genres
- Empty library will display "No books found" message

### ➕ Add New Book

1. Enter book title (required)
2. Enter book description (required)
3. Enter ISBN (required)
4. Specify number of authors and enter each author's name
5. Specify number of genres and enter each genre name
6. Review the information and confirm to save

### ✏️ Update Book

1. View all books with their IDs
2. Enter the ID of the book to update
3. Choose which field to update:
    - Title
    - Description
    - ISBN
    - Authors
    - Genres
4. Make changes and confirm to save

### 🗑️ Delete Book

1. View all books with their IDs
2. Enter the ID of the book to delete
3. Review the book details and confirm deletion

### 🔍 Search Functions

- By Title: Find books containing specific text in the title
- By Author: Find books by a specific author
- By Genre: Find books in a specific genre

---

## 🧪 Tests

The project includes unit tests using JUnit 5 and Mockito.

### Test Coverage

- Database connection and initialization
- Repository operations (CRUD)
- Controller logic
- Error handling scenarios

[![Imagen-de-Whats-App-2025-06-04-a-las-22-15-51-ea82ff6b.jpg](https://i.postimg.cc/SsptDQhv/Imagen-de-Whats-App-2025-06-04-a-las-22-15-51-ea82ff6b.jpg)](https://postimg.cc/HJB2Lgjw)

### Test Categories

- Unit Tests: Individual component testing
- Integration Tests: Database integration testing
- Mock Tests: External dependency testing

To run tests:

```bash

mvn test

Tests cover the repository logic (MySqlBookRepository) and database interaction simulations.
```

---

## 🎨 Console Interface Features

### Color-Coded Messages

- Green: Success messages and confirmations
- Red: Error messages and warnings
- Blue: Menu options and prompts
- Yellow: Information and status updates

### Input Validation

- Non-empty validation: Ensures required fields are not empty
- Numeric validation: Validates numeric inputs for IDs and counts
- Format validation: Ensures proper data formats

### Error Handling

- Database errors: Connection issues, SQL errors
- Input errors: Invalid formats, empty required fields
- Business logic errors: Duplicate entries, missing records

---

## 📁 Project Structure

```bash

library-T3/
├── src/
│   ├── main/
│   │   ├── java/org/LT3/
│   │   │   ├── config/         # Database configuration
│   │   │   ├── controller/     # Business logic controllers
│   │   │   ├── model/          # Entity models
│   │   │   ├── repository/     # Data access layer
│   │   │   ├── util/           # Utility classes
│   │   │   ├── view/           # User interface
│   │   │   └── Main.java       # Application entry point
│   │   └── resources/
│   │       └── schema.sql      # Database schema and sample data
│   └── test/
│       └── java/org/LT3/       # Unit tests
├── target/                     # Compiled classes and artifacts
├── .env                        # Environment variables (optional)
├── .gitignore                  # Git ignore rules
├── pom.xml                     # Maven configuration
└── README.md                   # This file

```

---

## 🚨 Error Handling

The application provides comprehensive error handling:

### Database Errors

- Connection failures
- SQL execution errors
- Transaction rollbacks
- Schema initialization issues

### Input Validation Errors

- Empty required fields
- Invalid numeric inputs
- Invalid ID references
- Duplicate entries

### Runtime Errors

- Resource management
- Memory issues
- Unexpected exceptions

---

## 📈 Future Enhancements

### Planned Features

- [ ] Web Interface: Spring Boot REST API
- [ ] Book Borrowing System: Track book loans and returns
- [ ] User Management: Library member management
- [ ] Advanced Search: Complex search queries
- [ ] Reports: Generate library statistics
- [ ] Book Reservations: Queue system for popular books
- [ ] Email Notifications: Overdue book reminders
- [ ] Book Categories: Hierarchical categorization
- [ ] Import/Export: CSV and Excel support
- [ ] Barcode Integration: ISBN barcode scanning

### Technical Improvements

- [ ] Connection Pooling: Improved database performance
- [ ] Caching: Redis integration for better performance
- [ ] Logging: Structured logging with Logback
- [ ] Configuration Management: Spring Boot profiles
- [ ] Docker Support: Containerization
- [ ] CI/CD Pipeline: Automated testing and deployment

---

## 🙏 Acknowledgments

- FemCoders25: For providing the educational framework
- MySQL Community: For the robust database system
- Apache Maven: For excellent dependency management
- JUnit Team: For comprehensive testing capabilities

---

## 📞 Support

If you encounter any issues or have questions:

1. Check the troubleshooting section in this README
2. Review the error messages - they provide specific guidance
3. Verify your database connection and credentials
4. Ensure all prerequisites are properly installed
5. Check the logs for detailed error information

---

## 👯‍♀️ Team

### [Paula Calvo](https://github.com/PCalvoGarcia)
### [Nadiia Alaieva](https://github.com/tizzifona)
### [Vita Poperechna](https://github.com/VitaPoperechna)

Happy Reading! 📖✨
