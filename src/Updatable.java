package src;
public interface Updatable {
    void updatePhoneNumber(String newPhoneNumber, String password);
    void updateEmail(String oldEmail, String newEmail, String password);
}
