package src;
public class Test {
    public static void main(String[] args) {
        try {
            DatabaseConnection.getConnection();
            System.out.println("Database connection successful!");
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
