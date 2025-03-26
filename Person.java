import java.time.LocalDate;
import java.util.ArrayList;

public class Person implements Authenticate, Updatable {
    private static int idCounter = 1;
    private static final ArrayList<Person> persons = new ArrayList<>();

    private final int id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phoneNumber;
    private String address;
    private String dateOfBirth;
    private final LocalDate registrationDate;

    public Person(String firstName, String lastName, String email, String password, String phoneNumber, String address, String dateOfBirth) {
        this.id = idCounter++;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.dateOfBirth = dateOfBirth;
        this.registrationDate = LocalDate.now();
        persons.add(this);
    }

    // Getter for firstName
    public String getFirstName() {
        return firstName;
    }

    // Getter for lastName
    public String getLastName() {
        return lastName;
    }

    @Override
    public boolean login(String email, String password) {
        return persons.stream().anyMatch(person -> person.email.equals(email) && person.password.equals(password));
    }

    @Override
    public void changedPassword(String newPassword, String oldPassword) {
        if (this.password.equals(oldPassword)) {
            this.password = newPassword;
            System.out.println("Password changed successfully.");
        } else {
            System.out.println("Incorrect old password. Try again.");
        }
    }

    @Override
    public void updateAddress(String newAddress, String password) {
        if (this.password.equals(password)) {
            this.address = newAddress;
            System.out.println("Address updated successfully.");
        } else {
            System.out.println("Incorrect password. Address update failed.");
        }
    }

    @Override
    public void updatePhoneNumber(String newPhoneNumber, String password) {
        if (this.password.equals(password)) {
            this.phoneNumber = newPhoneNumber;
            System.out.println("Phone number updated successfully.");
        } else {
            System.out.println("Incorrect password. Phone number update failed.");
        }
    }

    @Override
    public void updateEmail(String oldEmail, String newEmail, String password) {
        if (this.email.equals(oldEmail) && this.password.equals(password)) {
            this.email = newEmail;
            System.out.println("Email changed successfully.");
        } else {
            System.out.println("Incorrect password. Try again.");
        }
    }

    @Override
    public String toString() {
        return id + "," +
               firstName + "," +
               lastName + "," +
               email + "," +
               password + "," +
               phoneNumber + "," +
               address + "," +
               dateOfBirth + "," +
               registrationDate;
    }
}
