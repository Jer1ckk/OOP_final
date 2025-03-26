package src;
class Employee extends Person {

    public Employee(String firstName, String lastName, String email, String password, String phoneNumber, String accType) {
        super(firstName, lastName, email, password, phoneNumber, accType);
    }

    // Getter for first name (inherited from Person)
    public String getFirstName() {
        return super.getFirstName();
    }

    // Getter for last name (inherited from Person)
    public String getLastName() {
        return super.getLastName();
    }

}
