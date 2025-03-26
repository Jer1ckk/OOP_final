package src.GUI;

import java.util.List;
import src.User;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class Transfer extends JFrame {

    // Constructor for the Transfer class
    public Transfer(User currentUser, List<User> allUsers) {
        this.setTitle("Transfer Money");
        this.setSize(400, 300);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLayout(null);
        this.setLocationRelativeTo(null);

        // Labels and fields
        JLabel receiverLabel = new JLabel("Receiver Account ID:");
        receiverLabel.setBounds(50, 50, 150, 30);
        JTextField receiverField = new JTextField();
        receiverField.setBounds(200, 50, 150, 30);

        JLabel amountLabel = new JLabel("Amount:");
        amountLabel.setBounds(50, 100, 150, 30);
        JTextField amountField = new JTextField();
        amountField.setBounds(200, 100, 150, 30);

        JButton transferButton = new JButton("Transfer");
        transferButton.setBounds(150, 180, 100, 30);

        // Add action listener for the transfer button
        transferButton.addActionListener(e -> {
            try {
                int receiverId = Integer.parseInt(receiverField.getText());
                double amount = Double.parseDouble(amountField.getText());

                // Check for negative or zero amount
                if (amount <= 0) {
                    JOptionPane.showMessageDialog(this, "Transfer amount must be greater than zero.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Find the receiver by account ID
                User receiver = allUsers.stream()
                        .filter(user -> user.getAccountId() == receiverId)
                        .findFirst()
                        .orElse(null);

                if (receiver == null) {
                    JOptionPane.showMessageDialog(this, "Invalid Receiver Account ID. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Perform the transfer
                currentUser.transferMoney(receiver, amount);
                JOptionPane.showMessageDialog(this, "Transfer successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid input. Please enter valid numbers.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (IllegalStateException ex) {
                JOptionPane.showMessageDialog(this, "Transfer failed: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Add components to the frame
        this.add(receiverLabel);
        this.add(receiverField);
        this.add(amountLabel);
        this.add(amountField);
        this.add(transferButton);

        this.setVisible(true);
    }
}