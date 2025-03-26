package src.GUI;

import javax.swing.*;
import src.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Login extends JFrame {

    private void focusable(JButton... buttons) {
        for (JButton btn : buttons) {
            btn.setFocusable(false);
        }
    }

    Login() {
        // Frame setup
        this.setTitle("Login");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(500, 300);
        this.setResizable(false);
        this.setLayout(null);
        this.setLocationRelativeTo(null);

        // Email field
        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds((500 - 200) / 2 - 50, 50, 50, 30); // Left of field
        JTextField emailField = new JTextField();
        emailField.setBounds((500 - 200) / 2, 50, 200, 30); // Centered x

        // Password field
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds((500 - 200) / 2 - 80, 100, 80, 30); // Left of field
        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds((500 - 200) / 2, 100, 200, 30); // Centered x

        // Login button
        JButton loginButton = new JButton("Login");
        JButton registerButton = new JButton("Register");
        loginButton.setBounds((700 - 200) / 2, 150, 80, 30);
        registerButton.setBounds((500 - 200) / 2, 150, 90, 30);
        focusable(loginButton, registerButton);

        // Add action listener for login button
        loginButton.addActionListener(e -> {
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());
            String accountType = authenticateUser(email, password);
            if (accountType != null) {
                JOptionPane.showMessageDialog(this, "Login successful!");
                this.dispose(); // Close the login window
                if (accountType.equalsIgnoreCase("Customer")) {
                    this.dispose();
                    new Customer(email); // Open the Customer GUI
                } else if (accountType.equalsIgnoreCase("Staff")) {
                    this.dispose();
                    new Staff(); // Open the Staff GUI
                }
            } else {
                JOptionPane.showMessageDialog(this, "Invalid email or password. Please try again.");
            }
        });

        // Add components
        this.add(emailLabel);
        this.add(emailField);
        this.add(passwordLabel);
        this.add(passwordField);
        this.add(loginButton);
        this.add(registerButton);


        this.setVisible(true);
    }

    private String authenticateUser(String email, String password) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            // Check in the customers table
            String customerQuery = "SELECT password, account_type FROM customers WHERE email = ?";
            PreparedStatement customerStmt = conn.prepareStatement(customerQuery);
            customerStmt.setString(1, email);
            ResultSet customerRs = customerStmt.executeQuery();
    
            if (customerRs.next()) {
                String storedPassword = customerRs.getString("password");
                String accountType = customerRs.getString("account_type");
                if (password.equals(storedPassword)) {
                    return accountType; // Return the account type from the customers table
                }
            }
    
            // Check in the staff table
            String staffQuery = "SELECT password, account_type FROM staff WHERE email = ?";
            PreparedStatement staffStmt = conn.prepareStatement(staffQuery);
            staffStmt.setString(1, email);
            ResultSet staffRs = staffStmt.executeQuery();
    
            if (staffRs.next()) {
                String storedPassword = staffRs.getString("password");
                String accountType = staffRs.getString("account_type");
                if (password.equals(storedPassword)) {
                    return accountType; // Return the account type from the staff table
                }
            }
    
            return null; // Invalid credentials
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error connecting to the database: " + ex.getMessage());
            return null;
        }
    }

    public static void main(String[] args) {
        new Login();
    }
}