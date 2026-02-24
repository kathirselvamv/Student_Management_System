-- Create database

CREATE DATABASE IF NOT EXISTS student_management;
USE student_management;

-- Create users table for authentication
CREATE TABLE users (
    user_id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL,
    role ENUM('admin', 'user') DEFAULT 'user',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_username (username)
);

-- Create students table
CREATE TABLE students (
    student_id INT PRIMARY KEY AUTO_INCREMENT,
    roll_number VARCHAR(20) UNIQUE NOT NULL,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    phone VARCHAR(15),
    address TEXT,
    department VARCHAR(50),
    semester INT,
    cgpa DECIMAL(3,2),
    created_by INT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (created_by) REFERENCES users(user_id),
    INDEX idx_roll_number (roll_number),
    INDEX idx_email (email),
    INDEX idx_department (department)
);

-- Create audit_log table for tracking operations
CREATE TABLE audit_log (
    log_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT,
    action VARCHAR(50),
    table_name VARCHAR(50),
    record_id INT,
    details TEXT,
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    INDEX idx_timestamp (timestamp)
);

-- Insert default admin user (password: admin123 - you should hash this in real application)
INSERT INTO users (username, password, role)
VALUES ('admin', 'admin123', 'admin');

-- Insert sample student data
truncate table students;
INSERT INTO students
(roll_number, first_name, last_name, email, phone, address, department, semester, cgpa, created_by)
VALUES
('CS003','Kathirselvam','Kumar','Kathirselvamemail.com','9000000001','Chennai','Computer Science',3,8.10,1),
('CS004','Karthik','Rajan','karthik.rajan@email.com','9000000002','Coimbatore','Computer Science',5,8.75,1),
('CS005','Vignesh','Murugan','vignesh.murugan@email.com','9000000003','Madurai','Computer Science',2,7.90,1),
('CS006','Praveen','Kumar','praveen.kumar@email.com','9000000004','Salem','Computer Science',6,8.55,1),
('CS007','Sathish','Babu','sathish.babu@email.com','9000000005','Trichy','Computer Science',4,7.80,1),
('CS008','Dinesh','Kumar','dinesh.kumar@email.com','9000000006','Erode','Computer Science',7,8.95,1),
('CS009','Hari','Prasad','hari.prasad@email.com','9000000007','Chennai','Computer Science',1,7.65,1),
('CS010','Naveen','Raj','naveen.raj@email.com','9000000008','Vellore','Computer Science',5,8.20,1),
('CS011','Lokesh','Kannan','lokesh.kannan@email.com','9000000009','Tirunelveli','Computer Science',3,7.95,1),
('CS012','Ramesh','Kumar','ramesh.kumar@email.com','9000000010','Chennai','Computer Science',8,8.60,1),

('IT001','Surya','Prakash','surya.prakash@email.com','9000000011','Coimbatore','Information Technology',6,8.40,1),
('IT002','Ajith','Kumar','ajith.kumar@email.com','9000000012','Madurai','Information Technology',4,7.85,1),
('IT003','Vijay','Shankar','vijay.shankar@email.com','9000000013','Salem','Information Technology',5,9.10,1),
('IT004','Manikandan','R','manikandan.r@email.com','9000000014','Trichy','Information Technology',2,7.55,1),
('IT005','Saravanan','P','saravanan.p@email.com','9000000015','Erode','Information Technology',7,8.35,1),
('IT006','Madhan','Kumar','madhan.kumar@email.com','9000000016','Chennai','Information Technology',3,8.05,1),
('IT007','Gokul','Raj','gokul.raj@email.com','9000000017','Vellore','Information Technology',1,7.70,1),
('IT008','Ashwin','Kumar','ashwin.kumar@email.com','9000000018','Tirunelveli','Information Technology',6,8.80,1),
('IT009','Bharath','Kumar','bharath.kumar@email.com','9000000019','Chennai','Information Technology',4,7.95,1),
('IT010','Kishore','Kumar','kishore.kumar@email.com','9000000020','Coimbatore','Information Technology',8,8.65,1),

('EC002','Prakash','M','prakash.m@email.com','9000000021','Madurai','Electronics',5,7.90,1),
('EC003','Senthil','Kumar','senthil.kumar@email.com','9000000022','Salem','Electronics',3,8.25,1),
('EC004','Rajkumar','S','rajkumar.s@email.com','9000000023','Trichy','Electronics',6,8.70,1),
('EC005','Yuvaraj','P','yuvaraj.p@email.com','9000000024','Erode','Electronics',2,7.60,1),
('EC006','Muthukumar','R','muthukumar.r@email.com','9000000025','Chennai','Electronics',7,8.15,1),
('EC007','Tharun','Kumar','tharun.kumar@email.com','9000000026','Vellore','Electronics',4,7.85,1),
('EC008','Balaji','N','balaji.n@email.com','9000000027','Tirunelveli','Electronics',5,8.55,1),
('EC009','Suresh','Babu','suresh.babu@email.com','9000000028','Chennai','Electronics',1,7.45,1),
('EC010','Kamal','Raj','kamal.raj@email.com','9000000029','Coimbatore','Electronics',6,8.30,1),

('ME001','Ravichandran','K','ravichandran.k@email.com','9000000030','Madurai','Mechanical',5,7.75,1),
('ME002','Sivakumar','P','sivakumar.p@email.com','9000000031','Salem','Mechanical',3,8.05,1),
('ME003','Anand','Raj','anand.raj@email.com','9000000032','Trichy','Mechanical',6,8.45,1),
('ME004','Prithvi','Kumar','prithvi.kumar@email.com','9000000033','Erode','Mechanical',2,7.50,1),
('ME005','Aravind','S','aravind.s@email.com','9000000034','Chennai','Mechanical',7,8.20,1),
('ME006','Karthikeyan','M','karthikeyan.m@email.com','9000000035','Vellore','Mechanical',4,7.95,1),
('ME007','Loganathan','R','loganathan.r@email.com','9000000036','Tirunelveli','Mechanical',5,8.60,1),
('ME008','Nithin','Kumar','nithin.kumar@email.com','9000000037','Chennai','Mechanical',1,7.40,1),
('ME009','Vimal','Raj','vimal.raj@email.com','9000000038','Coimbatore','Mechanical',6,8.35,1),
('ME010','Sanjay','Kumar','sanjay.kumar@email.com','9000000039','Madurai','Mechanical',8,8.90,1),
('ME011','Deepak','Kumar','deepak.kumar@email.com','9000000040','Salem','Mechanical',3,7.85,1);