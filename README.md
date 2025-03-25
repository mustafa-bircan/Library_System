# Library Management System

A comprehensive console-based library management system built with Java, implementing SOLID principles and design patterns.

## Features

### Book Management
- Add, update, and delete books
- Search books by author or title
- Track book status (Available, Borrowed, Reserved)
- Manage book editions and prices
- Automatic due date calculation

### Reader Management
- Student and Faculty registration
- Department-based filtering
- Reader type-specific operations
- Automatic ID generation
- Reader status tracking

### Borrowing System
- Book lending and return operations
- Due date tracking
- Fine calculation
- Reader limit management
- Book availability checking

## Technical Features

### Design Patterns
- Builder Pattern for object creation
- Factory Pattern for reader types
- Strategy Pattern for fine calculation
- Observer Pattern for notifications

### SOLID Principles
- Single Responsibility Principle
- Open/Closed Principle
- Liskov Substitution Principle
- Interface Segregation Principle
- Dependency Inversion Principle

### Data Structures
- HashMap for efficient data storage
- ArrayList for dynamic collections
- Stream API for data processing

## Getting Started

### Prerequisites
- Java JDK 11 or higher
- Maven (for dependency management)

### Installation
1. Clone the repository
```bash
git clone https://github.com/yourusername/library-management-system.git
```

2. Navigate to the project directory
```bash
cd library-management-system
```

3. Compile the project
```bash
javac src/main/java/main/*.java
```

4. Run the application
```bash
java src.main.java.main.Main
```

## Usage

### Main Menu Options
1. Book Operations
   - Add new book
   - Update book information
   - Delete book
   - Search books
   - List all books

2. Reader Operations
   - Student registration
   - Faculty registration
   - Update reader information
   - Delete reader
   - List readers by department

3. Borrowing Operations
   - Lend book
   - Return book
   - Calculate fines
   - View borrowing history

## Project Structure





## Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## Version History

- v1.0 - Initial Release
  - Basic library management features
  - Reader and book management
  - Borrowing system

- v1.1 - Search Feature Update
  - Added book search functionality
  - Improved code organization
  - Enhanced user interface

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Acknowledgments

- Thanks to all contributors
- Inspired by real library management systems
- Built with modern Java practices
