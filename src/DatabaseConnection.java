package src;

import java.sql.*;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/bank_management_system";
    private static final String USER = "root"; // Replace with your MySQL username
    private static final String PASSWORD = "daraboy007"; // Replace with your MySQL password

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            // Debug: Verify the database being used
            try (Statement stmt = conn.createStatement()) {
                ResultSet rs = stmt.executeQuery("SELECT DATABASE()");
                if (rs.next()) {
                    System.out.println("Connected to database: " + rs.getString(1));
                }
            }
            return conn;
        } catch (ClassNotFoundException ex) {
            throw new SQLException("MySQL JDBC driver not found: " + ex.getMessage());
        } catch (SQLException ex) {
            throw new SQLException("Database connection failed: " + ex.getMessage());
        }
    }

    
}