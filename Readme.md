# 📚 Student Management System

A comprehensive CRUD-based Student Management System built with Core Java, JDBC, and MySQL. This application provides role-based access control with secure database operations and transaction management.

## 🚀 Features

### Core Functionality
- **Complete CRUD Operations**: Create, Read, Update, and Delete student records
- **Role-Based Access**: Admin and User roles with different permission levels
- **Secure Authentication**: Login system with role validation
- **Transaction Management**: Commit/Rollback for data integrity
- **Audit Logging**: Track all database operations

### Technical Highlights
- **Prepared Statements**: Protection against SQL injection
- **Indexed Queries**: Optimized database performance
- **SQL Joins**: Complex data retrieval across tables
- **Exception Handling**: Robust error management
- **Modular Architecture**: Clean separation of concerns

## 🛠️ Technology Stack

| Layer | Technology |
|-------|------------|
| **Language** | Java (Core) |
| **Database** | MySQL |
| **Connectivity** | JDBC |
| **Frontend** | HTML, CSS |
| **Build Tool** | Command-line compilation |
| **IDE** | Any Java IDE (Eclipse, IntelliJ, VS Code) |

## 📋 Prerequisites

Before running this application, ensure you have installed:

- Java JDK 8 or higher
- MySQL Server 5.7 or higher
- MySQL Connector/J (JDBC Driver)
- Any Java IDE or text editor

## 🔧 Installation & Setup

### 1. Clone the Repository
```bash
git clone https://github.com/yourusername/StudentManagementSystem.git
cd StudentManagementSystem
```

### 2. Database Setup

Execute the SQL script to create the database and tables:

```bash
mysql -u root -p < sql/database_setup.sql
```

Or manually run the SQL commands from `sql/database_setup.sql` in your MySQL client.

### 3. Configure Database Connection

Update the database credentials in `src/com/student/dao/DatabaseConnection.java`:

```java
private static final String URL = "jdbc:mysql://localhost:3306/student_management";
private static final String USERNAME = "root";  // Your MySQL username
private static final String PASSWORD = "your_password";  // Your MySQL password
```

### 4. Download MySQL Connector

Download the MySQL JDBC driver from:
- https://dev.mysql.com/downloads/connector/j/
- Place `mysql-connector-java-8.0.33.jar` in the project root directory

### 5. Compile the Application

```bash
# Navigate to src directory
cd src

# Compile all Java files
javac -cp .:../mysql-connector-java-8.0.33.jar com/student/model/*.java com/student/dao/*.java com/student/service/*.java com/student/main/*.java
```

For Windows, use semicolon instead of colon:
```bash
javac -cp .;../mysql-connector-java-8.0.33.jar com/student/model/*.java com/student/dao/*.java com/student/service/*.java com/student/main/*.java
```

### 6. Run the Application

```bash
java -cp .:../mysql-connector-java-8.0.33.jar com.student.main.StudentManagementApp
```

For Windows:
```bash
java -cp .;../mysql-connector-java-8.0.33.jar com.student.main.StudentManagementApp
```

## 📖 Usage Guide

### Default Login Credentials

| Role | Username | Password |
|------|----------|----------|
| **Admin** | admin | admin123 |
| **User** | (Create new) | (Set during creation) |

### Menu Options

Once logged in, you'll see the main menu:

```
╔════════════════════════════════╗
║          MAIN MENU            ║
╠════════════════════════════════╣
║ 1. View All Students          ║
║ 2. Add New Student            ║
║ 3. Update Student             ║
║ 4. Delete Student             ║
║ 5. Search Students            ║
║ 6. Filter by Department       ║
║ 7. View Audit Log             ║
║ 8. Logout                     ║
╚════════════════════════════════╝
```

### Role Permissions

| Feature | Admin | User |
|---------|-------|------|
| View Students | ✅ | ✅ |
| Add Student | ✅ | ❌ |
| Update Student | ✅ | ❌ |
| Delete Student | ✅ | ❌ |
| Search Students | ✅ | ✅ |
| Filter by Department | ✅ | ✅ |
| View Audit Log | ✅ | ❌ |

## 🏗️ Project Structure

```
StudentManagementSystem/
├── src/
│   └── com/
│       └── student/
│           ├── main/
│           │   └── StudentManagementApp.java    # Main application entry
│           ├── model/
│           │   ├── Student.java                  # Student entity
│           │   └── User.java                     # User entity
│           ├── dao/
│           │   ├── DatabaseConnection.java       # DB connection manager
│           │   ├── StudentDAO.java                # Student data operations
│           │   └── UserDAO.java                   # User authentication
│           └── service/
│               ├── StudentService.java            # Student business logic
│               └── UserService.java               # User business logic
├── web/
│   ├── css/
│   │   └── style.css                             # Stylesheet
│   └── html/
│       └── index.html                             # Web interface
├── sql/
│   └── database_setup.sql                         # Database schema
└── README.md                                       # Documentation
```

## 📊 Database Schema

### Users Table
| Column | Type | Description |
|--------|------|-------------|
| user_id | INT (PK) | Unique user identifier |
| username | VARCHAR(50) | Login username |
| password | VARCHAR(100) | Login password |
| role | ENUM | 'admin' or 'user' |
| created_at | TIMESTAMP | Account creation date |

### Students Table
| Column | Type | Description |
|--------|------|-------------|
| student_id | INT (PK) | Unique student identifier |
| roll_number | VARCHAR(20) | Unique roll number |
| first_name | VARCHAR(50) | Student's first name |
| last_name | VARCHAR(50) | Student's last name |
| email | VARCHAR(100) | Email address |
| phone | VARCHAR(15) | Contact number |
| address | TEXT | Physical address |
| department | VARCHAR(50) | Department name |
| semester | INT | Current semester |
| cgpa | DECIMAL(3,2) | Cumulative GPA |
| created_by | INT (FK) | User who added record |
| created_at | TIMESTAMP | Record creation date |
| updated_at | TIMESTAMP | Last update date |

### Audit Log Table
| Column | Type | Description |
|--------|------|-------------|
| log_id | INT (PK) | Unique log identifier |
| user_id | INT (FK) | User who performed action |
| action | VARCHAR(50) | INSERT/UPDATE/DELETE |
| table_name | VARCHAR(50) | Affected table |
| record_id | INT | Affected record ID |
| details | TEXT | Action details |
| timestamp | TIMESTAMP | Action timestamp |

## 🔒 Security Features

1. **SQL Injection Prevention**: All database queries use PreparedStatement
2. **Transaction Management**: Commit/Rollback for data consistency
3. **Role-Based Access Control**: Method-level permission checking
4. **Input Validation**: Type checking and validation
5. **Audit Trail**: Complete logging of all critical operations

## ⚡ Performance Optimizations

1. **Database Indexing**: Indexes on frequently queried columns
2. **Prepared Statements**: Query caching and reuse
3. **Connection Management**: Single connection instance
4. **Efficient Queries**: Optimized JOIN operations
5. **Batch Processing**: Bulk operations support

## 🐛 Troubleshooting

### Common Issues and Solutions

1. **MySQL Connection Error**
   - Verify MySQL is running: `sudo systemctl status mysql`
   - Check credentials in DatabaseConnection.java
   - Ensure database 'student_management' exists

2. **ClassNotFoundException**
   - Verify mysql-connector.jar is in classpath
   - Check file permissions

3. **Access Denied**
   - Verify username/password
   - Check user permissions in MySQL

## 🤝 Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📝 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 👥 Authors

- kathirselvam v - Initial work

## 🙏 Acknowledgments

- Oracle Java Documentation
- MySQL Documentation
- Stack Overflow Community

## 📧 Contact

For any queries or support, please contact:
- Email: kathirselvamv82@gmail.com
- GitHub: [kathirselvamv](https://github.com/kathirselvamv)

## 🚀 Future Enhancements

- [ ] Web-based interface using JSP/Servlets
- [ ] Password encryption using BCrypt
- [ ] Email notifications
- [ ] Export to Excel/PDF
- [ ] Advanced search filters
- [ ] Dashboard with charts
- [ ] REST API implementation
- [ ] Mobile app integration

---

**Made with ❤️ using Java**
