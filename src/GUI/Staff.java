package src.GUI;

import javax.swing.*;
import java.awt.*;

public class Staff extends JFrame {

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

    Staff() {
        this.setTitle("Staff");
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.setLocationRelativeTo(null);

        // Buttons
        JButton approveButton = new JButton("Approve Customer");
        JButton customerButton = new JButton("View Customer");
        JButton historyButton = new JButton("View History");
        JButton logoutButton = new JButton("Logout");
        focusable(approveButton, customerButton, historyButton, logoutButton);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.GREEN);
        buttonPanel.setPreferredSize(new Dimension(200, 100));

        // Resize all buttons
        setButtonSize(approveButton, customerButton, historyButton, logoutButton);

        buttonPanel.add(approveButton);
        buttonPanel.add(customerButton);
        buttonPanel.add(historyButton);
        buttonPanel.add(logoutButton);
        this.add(buttonPanel, BorderLayout.SOUTH); // Adding panel to the frame

        // Action listener for "Approve Customer" button
        approveButton.addActionListener(e -> new ApproveCustomerWindow());
        

        this.setVisible(true);
    }

    public static void main(String[] args) {
        new Staff();
    }
}