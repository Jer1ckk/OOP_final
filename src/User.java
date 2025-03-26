package src;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class User extends Person {
    private static int accountIdCounter = 1;
    private final int accountId;
    private double balance;
    private String pinCode;

    // List to store pending registrations
    private static final List<User> pendingRegistrations = new ArrayList<>();

    public User(String firstName, String lastName, String email, String password, String phoneNumber, String accType, String pinCode) {
        super(firstName, lastName, email, password, phoneNumber, accType);
        this.accountId = accountIdCounter++;
        this.pinCode = pinCode;
        this.balance = 0.0;
    }

    // Method to register a user (adds to pending list)
    public static boolean register(User user) {
        try {
            pendingRegistrations.add(user);
            System.out.println("User registration is pending approval: " + user.toString());
            return true; // Registration successful
        } catch (Exception e) {
            System.out.println("Error during registration: " + e.getMessage());
            return false; // Registration failed
        }
    }

    // Method to approve or reject a user
    public static boolean processRegistration(User user, Employee employee, boolean approve) {
        try {
            if (!"HR".equals(employee) && !"Manager".equals(employee)) {
                throw new SecurityException("Unauthorized to process user registrations.");
            }

            if (pendingRegistrations.contains(user)) {
                pendingRegistrations.remove(user);
                if (approve) {
                    System.out.println("User approved by " + employee.getFirstName() + " " + employee.getLastName() + ": " + user.toString());
                } else {
                    System.out.println("User rejected by " + employee.getFirstName() + " " + employee.getLastName() + ": " + user.toString());
                }
                return true; // Process successful
            } else {
                System.out.println("User not found in pending registrations.");
                return false; // User not found
            }
        } catch (SecurityException e) {
            System.out.println("Security Error: " + e.getMessage());
            return false; // Unauthorized
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
            return false; // General failure
        }
    }

    public void transferMoney(User receiver, double amount, String enteredPinCode) {
        try {
            if (amount <= 0) {
                throw new IllegalArgumentException("Transfer amount must be greater than zero.");
            }
            if (!this.pinCode.equals(enteredPinCode)) {
                throw new SecurityException("Invalid PIN code. Transfer denied.");
            }
            if (receiver == null) {
                throw new IllegalArgumentException("Receiver is not valid.");
            }
            if (this.balance < amount) {
                throw new IllegalStateException("Transfer failed. Insufficient balance.");
            }

            this.balance -= amount;
            receiver.balance += amount;

            System.out.println("Transfer successful! " + amount + " transferred from Account ID "
                               + this.accountId + " to Account ID " + receiver.accountId);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (SecurityException e) {
            System.out.println("Security Error: " + e.getMessage());
        } catch (IllegalStateException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }

    public void saveToFile(String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            // Write user data in a single line, comma-separated
            writer.write(
                this.toString()
            );
            writer.newLine(); // Move to the next line for the next user
            System.out.println("User data saved to file successfully.");
        } catch (IOException e) {
            System.out.println("Error saving user data to file: " + e.getMessage());
        }
    }

    @Override
    public String toString() {
        return super.toString() + "," + // Call the Person's toString method
               accountId + "," +
               balance + "," +
               pinCode;
    }
}
