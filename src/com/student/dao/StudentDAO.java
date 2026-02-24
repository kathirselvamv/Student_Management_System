package com.student.dao;

import com.student.model.Student;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {

    // Create student with transaction handling
    public boolean addStudent(Student student, int userId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        boolean success = false;

        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false); // Start transaction

            String query = "INSERT INTO students (roll_number, first_name, last_name, email, " +
                    "phone, address, department, semester, cgpa, created_by) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, student.getRollNumber());
            pstmt.setString(2, student.getFirstName());
            pstmt.setString(3, student.getLastName());
            pstmt.setString(4, student.getEmail());
            pstmt.setString(5, student.getPhone());
            pstmt.setString(6, student.getAddress());
            pstmt.setString(7, student.getDepartment());
            pstmt.setInt(8, student.getSemester());
            pstmt.setDouble(9, student.getCgpa());
            pstmt.setInt(10, userId);

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                // Get generated student ID
                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    int studentId = rs.getInt(1);
                    logAudit(conn, userId, "INSERT", "students", studentId,
                            "Added student: " + student.getRollNumber());
                }

                conn.commit(); // Commit transaction
                success = true;
                System.out.println("Student added successfully!");
            }

        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback(); // Rollback on error
                    System.out.println("Transaction rolled back due to error");
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return success;
    }

    // Read all students
    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        String query = "SELECT * FROM students ORDER BY student_id";

        try (Statement stmt = DatabaseConnection.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                students.add(mapResultSetToStudent(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return students;
    }

    // Get student by ID
    public Student getStudentById(int id) {
        String query = "SELECT * FROM students WHERE student_id = ?";

        try (PreparedStatement pstmt = DatabaseConnection.getConnection().prepareStatement(query)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToStudent(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    // Get students by department (using indexing)
    public List<Student> getStudentsByDepartment(String department) {
        List<Student> students = new ArrayList<>();
        String query = "SELECT * FROM students WHERE department = ?";

        try (PreparedStatement pstmt = DatabaseConnection.getConnection().prepareStatement(query)) {
            pstmt.setString(1, department);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                students.add(mapResultSetToStudent(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return students;
    }

    // Update student with transaction
    public boolean updateStudent(Student student, int userId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        boolean success = false;

        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false);

            String query = "UPDATE students SET first_name=?, last_name=?, email=?, " +
                    "phone=?, address=?, department=?, semester=?, cgpa=? " +
                    "WHERE student_id=?";

            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, student.getFirstName());
            pstmt.setString(2, student.getLastName());
            pstmt.setString(3, student.getEmail());
            pstmt.setString(4, student.getPhone());
            pstmt.setString(5, student.getAddress());
            pstmt.setString(6, student.getDepartment());
            pstmt.setInt(7, student.getSemester());
            pstmt.setDouble(8, student.getCgpa());
            pstmt.setInt(9, student.getStudentId());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                logAudit(conn, userId, "UPDATE", "students", student.getStudentId(),
                        "Updated student: " + student.getRollNumber());
                conn.commit();
                success = true;
                System.out.println("Student updated successfully!");
            }

        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return success;
    }

    // Delete student with transaction
    public boolean deleteStudent(int studentId, int userId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        boolean success = false;

        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false);

            String query = "DELETE FROM students WHERE student_id = ?";
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, studentId);

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                logAudit(conn, userId, "DELETE", "students", studentId,
                        "Deleted student with ID: " + studentId);
                conn.commit();
                success = true;
                System.out.println("Student deleted successfully!");
            }

        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return success;
    }

    // Search students using multiple criteria (demonstrating joins)
    public List<Student> searchStudents(String keyword) {
        List<Student> students = new ArrayList<>();
        String query = "SELECT s.*, u.username as created_by_username " +
                "FROM students s " +
                "LEFT JOIN users u ON s.created_by = u.user_id " +
                "WHERE s.first_name LIKE ? OR s.last_name LIKE ? " +
                "OR s.roll_number LIKE ? OR s.email LIKE ? " +
                "OR s.department LIKE ? " +
                "ORDER BY s.student_id";

        try (PreparedStatement pstmt = DatabaseConnection.getConnection().prepareStatement(query)) {
            String searchPattern = "%" + keyword + "%";
            for (int i = 1; i <= 5; i++) {
                pstmt.setString(i, searchPattern);
            }

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                students.add(mapResultSetToStudent(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return students;
    }

    // Helper method to map ResultSet to Student object
    private Student mapResultSetToStudent(ResultSet rs) throws SQLException {
        Student student = new Student();
        student.setStudentId(rs.getInt("student_id"));
        student.setRollNumber(rs.getString("roll_number"));
        student.setFirstName(rs.getString("first_name"));
        student.setLastName(rs.getString("last_name"));
        student.setEmail(rs.getString("email"));
        student.setPhone(rs.getString("phone"));
        student.setAddress(rs.getString("address"));
        student.setDepartment(rs.getString("department"));
        student.setSemester(rs.getInt("semester"));
        student.setCgpa(rs.getDouble("cgpa"));
        student.setCreatedBy(rs.getInt("created_by"));
        return student;
    }

    // Audit logging method
    private void logAudit(Connection conn, int userId, String action,
                          String tableName, int recordId, String details) {
        String query = "INSERT INTO audit_log (user_id, action, table_name, record_id, details) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, userId);
            pstmt.setString(2, action);
            pstmt.setString(3, tableName);
            pstmt.setInt(4, recordId);
            pstmt.setString(5, details);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}