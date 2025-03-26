package src.GUI;

import javax.swing.*;
import java.awt.*;


public class Customer extends JFrame {

    private void setButtonSize(JButton... buttons) {
        for (JButton btn : buttons) {
            btn.setPreferredSize(new Dimension(200, 50));
        }
    }

    private void focusable(JButton... buttons) {
        for(JButton btn : buttons) {
            btn.setFocusable(false);
        }
    }

    Customer() {
        this.setTitle("Customer");
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.setLocationRelativeTo(null);

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
        this.setVisible(true);
    }

    public static void main(String[] args) {
        new Customer();
    }
}
