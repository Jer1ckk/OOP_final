class Employee extends Person {
    private static int employeeIDCounter = 1;
    private final int employeeID;
    private String position;
    private double salary;
    private String hireDate;

    public Employee(String firstName, String lastName, String email, String password, String phoneNumber, String address, String dateOfBirth, String position, double salary, String hireDate) {
        super(firstName, lastName, email, password, phoneNumber, address, dateOfBirth);
        this.employeeID = employeeIDCounter++;
        this.position = position;
        this.salary = salary;
        this.hireDate = hireDate;
    }

    // Getter for position
    public String getPosition() {
        return position;
    }

    // Getter for first name (inherited from Person)
    public String getFirstName() {
        return super.getFirstName();
    }

    // Getter for last name (inherited from Person)
    public String getLastName() {
        return super.getLastName();
    }

    public void updatePosition(String newPosition, Employee authorizedBy) {
        if ("HR".equals(authorizedBy.position) || "Manager".equals(authorizedBy.position)) {
            this.position = newPosition;
            System.out.println("Position updated.");
        } else {
            System.out.println("Unauthorized to set position.");
        }
    }

    public void updateSalary(double newSalary, Employee authorizedBy) {
        try {
            if (newSalary <= 0) {
                throw new IllegalArgumentException("Salary must be greater than 0.");
            }
            if ("HR".equals(authorizedBy.position)) {
                this.salary = newSalary;
                System.out.println("Salary updated.");
            } else {
                System.out.println("Unauthorized to set salary.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    @Override
    public String toString() {
        return super.toString() + "," + // Call the Person's toString method
               employeeID + "," +
               position + "," +
               salary + "," +
               hireDate;
    }
}
