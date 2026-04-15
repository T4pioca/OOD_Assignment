package service;

import model.User;
import java.io.*;
import java.util.ArrayList;

// Abstraction: Separate service class for staff management business logic
public class StaffManagementService {

    // Encapsulation: private file path constant
    private final String FILE_PATH = "data/users.txt";

    // Collections: ArrayList to store User objects (OODJ Principle)
    // File I/O: BufferedReader with String.split() to parse staff data
    public ArrayList<User> loadAllUsers() {
        ArrayList<User> users = new ArrayList<>();
        // Exception Handling: try-with-resources for proper resource management
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                // String.split() to parse pipe-delimited data
                String[] data = line.split("\\|");
                if (data.length >= 9) {
                    User user = new User(
                        data[0], data[1], data[2], data[3],
                        data[4], data[5], data[6], data[7], data[8]
                    );
                    users.add(user);
                }
            }
        } catch (IOException e) {
            // Graceful error handling
            System.out.println("Error loading users: " + e.getMessage());
        }
        return users;
    }

    // Abstraction: Filter staff by role (Technician or CounterStaff)
    public ArrayList<User> getStaffList(ArrayList<User> allUsers) {
        ArrayList<User> staff = new ArrayList<>();
        for (User u : allUsers) {
            String role = u.getRole();
            if (role.equalsIgnoreCase("Technician") ||
                role.equalsIgnoreCase("CounterStaff") ||
                role.equalsIgnoreCase("Manager")) {
                staff.add(u);
            }
        }
        return staff;
    }

    // CRUD - Create: Register new staff with auto-generated ID
    // File I/O: BufferedWriter/FileWriter to save data
    public String getNextStaffId(ArrayList<User> allUsers) {
        int max = 0;
        for (User u : allUsers) {
            // Exception Handling: try-catch for NumberFormatException
            try {
                int num = Integer.parseInt(u.getId().substring(1));
                if (num > max) {
                    max = num;
                }
            } catch (NumberFormatException e) {
                // Skip malformed IDs gracefully
            }
        }
        return String.format("U%03d", max + 1);
    }

    // File I/O: BufferedWriter to save all users back to file
    public void saveAllUsers(ArrayList<User> allUsers) {
        // Exception Handling: try-with-resources for proper resource management
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (User u : allUsers) {
                String line = u.getId() + "|" +
                    u.getUsername() + "|" +
                    u.getPassword() + "|" +
                    u.getPhone() + "|" +
                    u.getEmail() + "|" +
                    u.getSecurityQuestion() + "|" +
                    u.getSecurityAnswer() + "|" +
                    u.getRole() + "|" +
                    u.getStatus();
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving users: " + e.getMessage());
        }
    }

    // Validation: Check if username already exists
    public boolean isUsernameExists(ArrayList<User> allUsers, String username) {
        for (User u : allUsers) {
            if (u.getUsername().equalsIgnoreCase(username)) {
                return true;
            }
        }
        return false;
    }

    // Validation: Check if email already exists
    public boolean isEmailExists(ArrayList<User> allUsers, String email) {
        for (User u : allUsers) {
            if (u.getEmail().equalsIgnoreCase(email)) {
                return true;
            }
        }
        return false;
    }
}
