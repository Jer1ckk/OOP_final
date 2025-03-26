package src.GUI;

import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.swing.*;

import src.DatabaseConnection;

public class Register extends JFrame {

    private void sizeField(JTextField field, int y) {
        field.setBounds((500 - 200) / 2, y, 200, 30);
    }

    private void sizeLabel(JLabel label, int y) {
        label.setBounds((500 - 200) / 2, y - 25, 200, 20); // Move label above the field
    }

    Register() {
        this.setTitle("Register");
        this.setSize(500, 600);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLayout(null);

        JLabel title = new JLabel("Customer Registration");
        title.setBounds((500 - 150) / 2, 10, 200, 30);

        JLabel firstNameLabel = new JLabel("First Name:");
        JTextField firstNameField = new JTextField();
        sizeLabel(firstNameLabel, 100);
        sizeField(firstNameField, 105);

        JLabel lastNameLabel = new JLabel("Last Name:");
        JTextField lastNameField = new JTextField();
        sizeLabel(lastNameLabel, 180);
        sizeField(lastNameField, 185);

        JLabel emailLabel = new JLabel("Email:");
        JTextField emailField = new JTextField();
        sizeLabel(emailLabel, 260);
        sizeField(emailField, 265);

        JLabel phoneLabel = new JLabel("Phone Number:");
        JTextField phoneField = new JTextField();
        sizeLabel(phoneLabel, 340);
        sizeField(phoneField, 345);

        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField();
        sizeLabel(passwordLabel, 420);
        sizeField(passwordField, 425);

        JButton registerButton = new JButton("Register");
        registerButton.setBounds((500 - 100) / 2, 480, 100, 30);
        registerButton.setFocusable(false);

        // Add action listener for the register button
        registerButton.addActionListener(e -> {
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            String email = emailField.getText();
            String phoneNumber = phoneField.getText();
            String password = new String(passwordField.getPassword());

            if (registerUser(firstName, lastName, email, phoneNumber, password)) {
                JOptionPane.showMessageDialog(this, "Registration successful! Awaiting staff approval.");
                this.dispose(); // Close the registration window
            } else {
                JOptionPane.showMessageDialog(this, "Registration failed. Please try again.");
            }
        });

        // Add components
        this.add(title);
        this.add(firstNameLabel);
        this.add(firstNameField);
        this.add(lastNameLabel);
        this.add(lastNameField);
        this.add(emailLabel);
        this.add(emailField);
        this.add(phoneLabel);
        this.add(phoneField);
        this.add(passwordLabel);
        this.add(passwordField);
        this.add(registerButton);

        this.setVisible(true);
    }

    private boolean registerUser(String firstName, String lastName, String email, String phoneNumber, String password) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "INSERT INTO account_request (first_name, last_name, phone_number, email, password, account_type, request_date, status) " +
               "VALUES (?, ?, ?, ?, ?, 'Customer', CURRENT_DATE, 'Pending')";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, firstName);
            stmt.setString(2, lastName);
            stmt.setString(3, phoneNumber);
            stmt.setString(4, email);
            stmt.setString(5, password);

            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0; // Return true if the insertion was successful
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error connecting to the database: " + ex.getMessage());
            return false;
        }
    }

    public static void main(String[] args) {
        new Register();
    }
}
