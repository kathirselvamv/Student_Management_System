package com.student.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/student_management";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "Ks@122004"; // Change this to your MySQL password
    private static Connection connection = null;

    public static Connection getConnection() {
        if (connection == null) {
            try {
                // Try both MySQL driver class names for compatibility
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                } catch (ClassNotFoundException e) {
                    Class.forName("com.mysql.jdbc.Driver");
                }

                connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                System.out.println(" Database connected successfully!");
            } catch (ClassNotFoundException e) {
                System.err.println(" MySQL JDBC Driver not found!");
                System.err.println("Please add MySQL Connector JAR to your project:");
                System.err.println("1. Download from: https://dev.mysql.com/downloads/connector/j/");
                System.err.println("2. Add the JAR file to your project classpath");
                e.printStackTrace();
                throw new RuntimeException("MySQL JDBC Driver not found");
            } catch (SQLException e) {
                System.err.println(" Database connection failed!");
                System.err.println("Please check:");
                System.err.println("1. MySQL server is running");
                System.err.println("2. Database 'student_management' exists");
                System.err.println("3. Username and password are correct");
                e.printStackTrace();
                throw new RuntimeException("Error connecting to database");
            }
        }
        return connection;
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Database connection closed.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // Test connection method
    public static void testConnection() {
        try {
            Connection conn = getConnection();
            if (conn != null) {
                System.out.println(" Connection test successful!");
                System.out.println("Database: " + conn.getCatalog());
                System.out.println("Driver: " + conn.getMetaData().getDriverName());
            }
        } catch (Exception e) {
            System.err.println(" Connection test failed: " + e.getMessage());
        }
    }
}