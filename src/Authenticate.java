package src;
public interface Authenticate {
    boolean login(String email, String password);
    void changedPassword(String newPassword, String oldPassword);
}
