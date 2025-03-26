package src.GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import src.DatabaseConnection;

public class ApproveCustomerWindow extends JFrame {

    public ApproveCustomerWindow() {
        this.setTitle("Approve Customer Requests");
        this.setSize(800, 600);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.setLocationRelativeTo(null);
        this.setResizable(false);

        // Table for pending approvals
        String[] columnNames = {"ID", "First Name", "Last Name", "Phone Number", "Email", "Request Date", "Status"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JTable approvalTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(approvalTable);
        this.add(scrollPane, BorderLayout.CENTER);

        // Load pending requests into the table
        loadPendingRequests(tableModel);

        // Approve and Reject buttons
        JPanel buttonPanel = new JPanel();
        JButton approveButton = new JButton("Approve");
        JButton rejectButton = new JButton("Reject");
        buttonPanel.add(approveButton);
        buttonPanel.add(rejectButton);
        this.add(buttonPanel, BorderLayout.SOUTH);

        // Approve button action
        approveButton.addActionListener(e -> {
            int selectedRow = approvalTable.getSelectedRow();
            if (selectedRow != -1) {
                int id = Integer.parseInt(tableModel.getValueAt(selectedRow, 0).toString());
                approveCustomer(id);
                tableModel.removeRow(selectedRow); // Remove the approved row from the table
            } else {
                JOptionPane.showMessageDialog(this, "Please select a customer to approve.");
            }
        });

        // Reject button action
        rejectButton.addActionListener(e -> {
            int selectedRow = approvalTable.getSelectedRow();
            if (selectedRow != -1) {
                int id = Integer.parseInt(tableModel.getValueAt(selectedRow, 0).toString());
                rejectCustomer(id);
                tableModel.removeRow(selectedRow); // Remove the rejected row from the table
            } else {
                JOptionPane.showMessageDialog(this, "Please select a customer to reject.");
            }
        });

        this.setVisible(true);
    }

    private void loadPendingRequests(DefaultTableModel tableModel) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT id, first_name, last_name, phone_number, email, request_date, status FROM account_request";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                String phoneNumber = rs.getString("phone_number");
                String email = rs.getString("email");
                String requestDate = rs.getString("request_date");
                String status = rs.getString("status");
                tableModel.addRow(new Object[]{id, firstName, lastName, phoneNumber, email, requestDate, status});
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error loading pending requests: " + ex.getMessage());
        }
    }

    private String generateAccountNumber() {
        String accountNumber;
        try (Connection conn = DatabaseConnection.getConnection()) {
            do {
                accountNumber = String.valueOf((long) (Math.random() * 1_000_000_0000L)); // Generate a random 10-digit number
                String query = "SELECT COUNT(*) FROM accounts WHERE account_number = ?";
                PreparedStatement stmt = conn.prepareStatement(query);
                stmt.setString(1, accountNumber);
                ResultSet rs = stmt.executeQuery();
                rs.next();
                if (rs.getInt(1) == 0) {
                    break; // Account number is unique
                }
            } while (true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error generating account number: " + ex.getMessage());
            accountNumber = null;
        }
        return accountNumber;
    }

    private void approveCustomer(int id) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            // Update the status of the customer to "Approved"
            String updateStatusQuery = "UPDATE account_request SET status = 'Approved' WHERE id = ?";
            PreparedStatement updateStatusStmt = conn.prepareStatement(updateStatusQuery);
            updateStatusStmt.setInt(1, id);
            updateStatusStmt.executeUpdate();
    
            // Move the customer from account_request to customers table
            String insertQuery = "INSERT INTO customers (first_name, last_name, phone_number, email, password, account_type) " +
                    "SELECT first_name, last_name, phone_number, email, password, 'customer' FROM account_request WHERE id = ?";
            PreparedStatement insertStmt = conn.prepareStatement(insertQuery, PreparedStatement.RETURN_GENERATED_KEYS);
            insertStmt.setInt(1, id);
            insertStmt.executeUpdate();
    
            // Get the generated customer ID
            ResultSet generatedKeys = insertStmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                int customerId = generatedKeys.getInt(1);
    
                // Generate a unique account number
                String accountNumber = generateAccountNumber();
    
                // Insert the account number into the accounts table
                String accountQuery = "INSERT INTO accounts (customer_id, account_number, balance) VALUES (?, ?, ?)";
                PreparedStatement accountStmt = conn.prepareStatement(accountQuery);
                accountStmt.setInt(1, customerId);
                accountStmt.setString(2, accountNumber);
                accountStmt.setDouble(3, 0.0); // Initial balance is 0.0
                accountStmt.executeUpdate();
            }
    
            // Delete the customer from account_request table
            String deleteQuery = "DELETE FROM account_request WHERE id = ?";
            PreparedStatement deleteStmt = conn.prepareStatement(deleteQuery);
            deleteStmt.setInt(1, id);
            deleteStmt.executeUpdate();
    
            // Reset the AUTO_INCREMENT value for the account_request table
            String resetAutoIncrementQuery = "ALTER TABLE account_request AUTO_INCREMENT = 1";
            PreparedStatement resetStmt = conn.prepareStatement(resetAutoIncrementQuery);
            resetStmt.executeUpdate();
    
            JOptionPane.showMessageDialog(this, "Customer approved successfully! Account number generated.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error approving customer: " + ex.getMessage());
        }
    }

    private void rejectCustomer(int id) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            // Update the status of the customer to "Rejected"
            String updateStatusQuery = "UPDATE account_request SET status = 'Rejected' WHERE id = ?";
            PreparedStatement updateStatusStmt = conn.prepareStatement(updateStatusQuery);
            updateStatusStmt.setInt(1, id);
            updateStatusStmt.executeUpdate();

            // Delete the customer from account_request table
            String deleteQuery = "DELETE FROM account_request WHERE id = ?";
            PreparedStatement deleteStmt = conn.prepareStatement(deleteQuery);
            deleteStmt.setInt(1, id);
            deleteStmt.executeUpdate();

            JOptionPane.showMessageDialog(this, "Customer rejected successfully!");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error rejecting customer: " + ex.getMessage());
        }
    }
}