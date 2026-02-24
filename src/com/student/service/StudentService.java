package com.student.service;

import com.student.dao.StudentDAO;
import com.student.model.Student;
import java.util.List;
import java.util.Scanner;

public class StudentService {

    private final StudentDAO studentDAO;
    private final Scanner scanner;

    public StudentService() {
        this.studentDAO = new StudentDAO();
        this.scanner = new Scanner(System.in);
    }

    public void addStudent(int userId) {
        System.out.println("\nAdd New Student");

        try {
            Student student = new Student();

            System.out.print("Roll Number: ");
            student.setRollNumber(scanner.nextLine().trim());

            System.out.print("First Name: ");
            student.setFirstName(scanner.nextLine().trim());

            System.out.print("Last Name: ");
            student.setLastName(scanner.nextLine().trim());

            System.out.print("Email: ");
            student.setEmail(scanner.nextLine().trim());

            System.out.print("Phone: ");
            student.setPhone(scanner.nextLine().trim());

            System.out.print("Address: ");
            student.setAddress(scanner.nextLine().trim());

            System.out.print("Department: ");
            student.setDepartment(scanner.nextLine().trim());

            System.out.print("Semester: ");
            student.setSemester(Integer.parseInt(scanner.nextLine().trim()));

            System.out.print("CGPA: ");
            student.setCgpa(Double.parseDouble(scanner.nextLine().trim()));

            boolean added = studentDAO.addStudent(student, userId);
            System.out.println(added ? "Student added successfully." : "Failed to add student.");

        } catch (NumberFormatException e) {
            System.out.println("Invalid number format.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void viewAllStudents() {
        System.out.println("\nAll Students");

        List<Student> students = studentDAO.getAllStudents();

        if (students.isEmpty()) {
            System.out.println("No students found.");
            return;
        }

        System.out.println("----------------------------------------------------------------------------------------");
        System.out.printf("%-5s %-12s %-20s %-25s %-15s %-5s %-6s%n",
                "ID", "Roll No", "Name", "Email", "Department", "Sem", "CGPA");
        System.out.println("----------------------------------------------------------------------------------------");

        for (Student s : students) {
            System.out.printf("%-5d %-12s %-20s %-25s %-15s %-5d %-6.2f%n",
                    s.getStudentId(),
                    s.getRollNumber(),
                    s.getFirstName() + " " + s.getLastName(),
                    s.getEmail(),
                    s.getDepartment(),
                    s.getSemester(),
                    s.getCgpa());
        }

        System.out.println("----------------------------------------------------------------------------------------");
    }

    public void updateStudent(int userId) {
        System.out.println("\nUpdate Student");
        System.out.print("Enter Student ID: ");

        try {
            int id = Integer.parseInt(scanner.nextLine().trim());
            Student student = studentDAO.getStudentById(id);

            if (student == null) {
                System.out.println("Student not found.");
                return;
            }

            System.out.println("\nPress Enter to keep existing value.");

            updateField("First Name", student.getFirstName(), student::setFirstName);
            updateField("Last Name", student.getLastName(), student::setLastName);
            updateField("Email", student.getEmail(), student::setEmail);
            updateField("Phone", student.getPhone(), student::setPhone);
            updateField("Address", student.getAddress(), student::setAddress);
            updateField("Department", student.getDepartment(), student::setDepartment);

            System.out.print("Semester [" + student.getSemester() + "]: ");
            String semInput = scanner.nextLine().trim();
            if (!semInput.isEmpty()) {
                student.setSemester(Integer.parseInt(semInput));
            }

            System.out.print("CGPA [" + student.getCgpa() + "]: ");
            String cgpaInput = scanner.nextLine().trim();
            if (!cgpaInput.isEmpty()) {
                student.setCgpa(Double.parseDouble(cgpaInput));
            }

            boolean updated = studentDAO.updateStudent(student, userId);
            System.out.println(updated ? "Student updated successfully." : "Failed to update student.");

        } catch (NumberFormatException e) {
            System.out.println("Invalid number format.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void deleteStudent(int userId) {
        System.out.println("\nDelete Student");
        System.out.print("Enter Student ID: ");

        try {
            int id = Integer.parseInt(scanner.nextLine().trim());

            System.out.print("Confirm delete (y/n): ");
            String confirm = scanner.nextLine().trim();

            if (!confirm.equalsIgnoreCase("y")) {
                System.out.println("Deletion cancelled.");
                return;
            }

            boolean deleted = studentDAO.deleteStudent(id, userId);
            System.out.println(deleted ? "Student deleted successfully." : "Failed to delete student.");

        } catch (NumberFormatException e) {
            System.out.println("Invalid input.");
        }
    }

    public void searchStudents() {
        System.out.println("\nSearch Students");
        System.out.print("Enter keyword: ");

        String keyword = scanner.nextLine().trim();
        List<Student> students = studentDAO.searchStudents(keyword);

        if (students.isEmpty()) {
            System.out.println("No matching students found.");
            return;
        }

        students.forEach(System.out::println);
    }

    public void filterByDepartment() {
        System.out.println("\nFilter by Department");
        System.out.print("Enter department: ");

        String department = scanner.nextLine().trim();
        List<Student> students = studentDAO.getStudentsByDepartment(department);

        if (students.isEmpty()) {
            System.out.println("No students found in this department.");
            return;
        }

        students.forEach(System.out::println);
    }

    private void updateField(String label, String currentValue, java.util.function.Consumer<String> setter) {
        System.out.print(label + " [" + currentValue + "]: ");
        String input = scanner.nextLine().trim();
        if (!input.isEmpty()) {
            setter.accept(input);
        }
    }
}