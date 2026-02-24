package com.student.main;

import com.student.dao.DatabaseConnection;
import com.student.service.StudentService;
import com.student.service.UserService;
import java.util.Scanner;

public class StudentManagementApp {

    private static Scanner scanner = new Scanner(System.in);
    private static UserService userService = new UserService();
    private static StudentService studentService = new StudentService();

    public static void main(String[] args) {

        System.out.println("STUDENT MANAGEMENT SYSTEM");
        System.out.println("--------------------------");

        // Login first
        if (!userService.login()) {
            System.out.println("Exiting system...");
            return;
        }

        boolean running = true;

        while (running) {
            displayMenu();
            int choice = getUserChoice();

            switch (choice) {

                case 1:
                    studentService.viewAllStudents();
                    break;

                case 2:
                    if (checkAdminAccess()) {
                        studentService.addStudent(userService.getCurrentUserId());
                    }
                    break;

                case 3:
                    if (checkAdminAccess()) {
                        studentService.updateStudent(userService.getCurrentUserId());
                    }
                    break;

                case 4:
                    if (checkAdminAccess()) {
                        studentService.deleteStudent(userService.getCurrentUserId());
                    }
                    break;

                case 5:
                    studentService.searchStudents();
                    break;

                case 6:
                    studentService.filterByDepartment();
                    break;

                case 7:
                    System.out.println("\nAUDIT LOG");
                    System.out.println("Feature coming soon...");
                    break;

                case 8:
                    userService.logout();
                    running = false;
                    System.out.println("Thank you for using Student Management System.");
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }

            if (running && choice != 8) {
                System.out.print("\nPress Enter to continue...");
                scanner.nextLine();
            }
        }

        DatabaseConnection.closeConnection();
        scanner.close();
    }

    private static void displayMenu() {
        System.out.println("\nMAIN MENU");
        System.out.println("1. View All Students");
        System.out.println("2. Add New Student");
        System.out.println("3. Update Student");
        System.out.println("4. Delete Student");
        System.out.println("5. Search Students");
        System.out.println("6. Filter by Department");
        System.out.println("7. View Audit Log");
        System.out.println("8. Logout");
        System.out.print("Enter your choice: ");
    }

    private static int getUserChoice() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private static boolean checkAdminAccess() {
        if (!userService.isAdmin()) {
            System.out.println("Access denied. Admin privileges required.");
            return false;
        }
        return true;
    }
}