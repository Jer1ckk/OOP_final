public interface Updatable {
    void updateAddress(String newAddress, String password);
    void updatePhoneNumber(String newPhoneNumber, String password);
    void updateEmail(String oldEmail, String newEmail, String password);
}
