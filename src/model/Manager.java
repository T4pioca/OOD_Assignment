package model;

// Inheritance: Manager extends User superclass (OODJ Principle)
public class Manager extends User {

    // Encapsulation: private attributes with public getters/setters
    private String department;
    private int employeesManagedCount;

    // Constructor using super() to call User superclass constructor
    public Manager(String id, String username, String password, String phone,
                   String email, String securityQuestion, String securityAnswer,
                   String role, String status, String department, int employeesManagedCount) {
        super(id, username, password, phone, email, securityQuestion, securityAnswer, role, status);
        this.department = department;
        this.employeesManagedCount = employeesManagedCount;
    }

    // Overloaded constructor for creating Manager from existing User data
    public Manager(String id, String username, String password, String phone,
                   String email, String securityQuestion, String securityAnswer,
                   String role, String status) {
        super(id, username, password, phone, email, securityQuestion, securityAnswer, role, status);
        this.department = "Management";
        this.employeesManagedCount = 0;
    }

    // Encapsulation: Getters and Setters for private attributes
    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public int getEmployeesManagedCount() {
        return employeesManagedCount;
    }

    public void setEmployeesManagedCount(int employeesManagedCount) {
        this.employeesManagedCount = employeesManagedCount;
    }

    // Input validation methods for robustness
    public static boolean isValidPhone(String phone) {
        return phone != null && phone.matches("\\d{10,12}");
    }

    public static boolean isValidEmail(String email) {
        return email != null && email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$");
    }

    // Override toString() for manager-specific display (Polymorphism)
    @Override
    public String toString() {
        return "Manager{" +
                "id=" + getId() +
                ", username='" + getUsername() + '\'' +
                ", department='" + department + '\'' +
                ", employeesManaged=" + employeesManagedCount +
                ", role='" + getRole() + '\'' +
                '}';
    }
}
