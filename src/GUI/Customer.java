package src.GUI;

import src.User;
import src.DatabaseConnection;
import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class Customer extends JFrame {

    private void setButtonSize(JButton... buttons) {
        for (JButton btn : buttons) {
            btn.setPreferredSize(new Dimension(200, 50));
        }
    }

    private void focusable(JButton... buttons) {
        for (JButton btn : buttons) {
            btn.setFocusable(false);
        }
    }

    public Customer(String loggedInEmail) {
        this.setTitle("Customer");
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.setLocationRelativeTo(null);

        // Fetch the current user and all users from the database
        User currentUser = getCurrentUser(loggedInEmail);
        List<User> allUsers = getAllUsers();

        if (currentUser == null) {
            JOptionPane.showMessageDialog(this, "Failed to load user data.", "Error", JOptionPane.ERROR_MESSAGE);
            this.dispose();
            return;
        }

        JButton viewButton = new JButton("My Account");
        JButton transferButton = new JButton("Transfer");
        JButton historyButton = new JButton("History");
        JButton logoutButton = new JButton("Logout");
        focusable(viewButton, transferButton, historyButton, logoutButton);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.GREEN);
        buttonPanel.setPreferredSize(new Dimension(200, 100));
        setButtonSize(viewButton, transferButton, historyButton, logoutButton);

        buttonPanel.add(viewButton);
        buttonPanel.add(transferButton);
        buttonPanel.add(historyButton);
        buttonPanel.add(logoutButton);

        this.add(buttonPanel, BorderLayout.SOUTH);

        // Pass the current user and all users to the Transfer class
        transferButton.addActionListener(e -> new Transfer(currentUser, allUsers));

        this.setVisible(true);
    }

    private User getCurrentUser(String email) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM customers WHERE email = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                User user = new User(
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("phone_number"),
                        rs.getString("account_type")
                );
                user.setBalance(rs.getDouble("balance")); // Assuming the balance column exists
                return user;
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error fetching current user: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }

    private List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM customers";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                User user = new User(
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("phone_number"),
                        rs.getString("account_type")
                );
                user.setBalance(rs.getDouble("balance")); // Assuming the balance column exists
                users.add(user);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error fetching all users: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        return users;
    }

}