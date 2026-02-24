package com.student.service;

import com.student.dao.UserDAO;
import com.student.model.User;
import java.util.Scanner;

public class UserService {
    private UserDAO userDAO;
    private Scanner scanner;
    private User currentUser;

    public UserService() {
        this.userDAO = new UserDAO();
        this.scanner = new Scanner(System.in);
    }

    public boolean login() {
        System.out.println("\n=== LOGIN ===");
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        currentUser = userDAO.authenticateUser(username, password);

        if (currentUser != null) {
            System.out.println("\n Login successful! Welcome, " + username);
            System.out.println("Role: " + currentUser.getRole());
            return true;
        } else {
            System.out.println("\n Invalid username or password!");
            return false;
        }
    }

    public boolean isAdmin() {
        return currentUser != null && currentUser.isAdmin();
    }

    public int getCurrentUserId() {
        return currentUser != null ? currentUser.getUserId() : -1;
    }

    public void logout() {
        currentUser = null;
        System.out.println("Logged out successfully!");
    }
}