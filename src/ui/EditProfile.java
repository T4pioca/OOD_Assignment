package ui;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import model.User;

public class EditProfile {

    public EditProfile(User user) {

        JFrame frame = new JFrame("Edit Profile");
        frame.setSize(450, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);

        JLabel title = new JLabel("Edit Profile");
        title.setBounds(170, 20, 120, 25);
        frame.add(title);

        JLabel lblUsername = new JLabel("Username:");
        lblUsername.setBounds(30, 60, 120, 25);
        frame.add(lblUsername);

        JTextField txtUsername = new JTextField(user.getUsername());
        txtUsername.setBounds(180, 60, 200, 25);
        txtUsername.setEditable(false);
        frame.add(txtUsername);

        JLabel lblPhone = new JLabel("Phone:");
        lblPhone.setBounds(30, 100, 120, 25);
        frame.add(lblPhone);

        JTextField txtPhone = new JTextField(user.getPhone());
        txtPhone.setBounds(180, 100, 200, 25);
        frame.add(txtPhone);

        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setBounds(30, 140, 120, 25);
        frame.add(lblEmail);

        JTextField txtEmail = new JTextField(user.getEmail());
        txtEmail.setBounds(180, 140, 200, 25);
        frame.add(txtEmail);

        JLabel lblPassword = new JLabel("New Password:");
        lblPassword.setBounds(30, 180, 120, 25);
        frame.add(lblPassword);

        JPasswordField txtPassword = new JPasswordField();
        txtPassword.setBounds(180, 180, 200, 25);
        frame.add(txtPassword);

        JLabel lblConfirmPassword = new JLabel("Confirm Password:");
        lblConfirmPassword.setBounds(30, 220, 140, 25);
        frame.add(lblConfirmPassword);

        JPasswordField txtConfirmPassword = new JPasswordField();
        txtConfirmPassword.setBounds(180, 220, 200, 25);
        frame.add(txtConfirmPassword);

        JLabel lblSecurityQuestion = new JLabel("Security Question:");
        lblSecurityQuestion.setBounds(30, 260, 120, 25);
        frame.add(lblSecurityQuestion);

        String[] questions = {
        	"What is your favorite color?",
            "What is your pet's name?",
            "What city were you born in?",
            "What is your favorite food?"	
        };

        JComboBox<String> cmbQuestion = new JComboBox<>(questions);
        cmbQuestion.setBounds(180, 260, 200, 25);
        cmbQuestion.setSelectedItem(user.getSecurityQuestion());
        frame.add(cmbQuestion);

        JLabel lblAnswer = new JLabel("Security Answer:");
        lblAnswer.setBounds(30, 300, 120, 25);
        frame.add(lblAnswer);

        JTextField txtAnswer = new JTextField(user.getSecurityAnswer());
        txtAnswer.setBounds(180, 300, 200, 25);
        frame.add(txtAnswer);

        JCheckBox showPassword = new JCheckBox("Show Password");
        showPassword.setBounds(180, 330, 140, 25);
        frame.add(showPassword);

        JButton btnSave = new JButton("Save");
        btnSave.setBounds(90, 380, 100, 30);
        frame.add(btnSave);

        JButton btnBack = new JButton("Back");
        btnBack.setBounds(230, 380, 100, 30);
        frame.add(btnBack);

        char defaultPasswordChar = txtPassword.getEchoChar();
        char defaultConfirmChar = txtConfirmPassword.getEchoChar();

        showPassword.addActionListener(e -> {
            if (showPassword.isSelected()) {
                txtPassword.setEchoChar((char) 0);
                txtConfirmPassword.setEchoChar((char) 0);
            } else {
                txtPassword.setEchoChar(defaultPasswordChar);
                txtConfirmPassword.setEchoChar(defaultConfirmChar);
            }
        });

        btnSave.addActionListener(e -> {
            String newPhone = txtPhone.getText().trim();
            String newEmail = txtEmail.getText().trim();
            String newPassword = new String(txtPassword.getPassword()).trim();
            String confirmPassword = new String(txtConfirmPassword.getPassword()).trim();
            String newQuestion = cmbQuestion.getSelectedItem().toString();
            String newAnswer = txtAnswer.getText().trim();

            if (newPhone.isEmpty() || newEmail.isEmpty() || newQuestion.isEmpty() || newAnswer.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please fill in all fields.");
                return;
            }

            if (!newPassword.isEmpty() || !confirmPassword.isEmpty()) {
                if (!newPassword.equals(confirmPassword)) {
                    JOptionPane.showMessageDialog(frame, "Password and Confirm Password do not match.");
                    return;
                }
            }

            String finalPassword = newPassword.isEmpty() ? user.getPassword() : newPassword;

            boolean updated = updateProfile(
                user.getId(),
                newPhone,
                newEmail,
                finalPassword,
                newQuestion,
                newAnswer
            );

            if (updated) {
                JOptionPane.showMessageDialog(frame, "Profile updated successfully.");

                user.setPhone(newPhone);
                user.setEmail(newEmail);
                user.setPassword(finalPassword);
                user.setSecurityQuestion(newQuestion);
                user.setSecurityAnswer(newAnswer);

                frame.dispose();

                if (user.getRole().equals("Manager")) {
                    new ManagerMenu(user);
                } else if (user.getRole().equals("CounterStaff")) {
                    new CounterStaffMenu(user);
                } else if (user.getRole().equals("Technician")) {
                    new TechnicianMenu(user);
                } else if (user.getRole().equals("Customer")) {
                    new CustomerMenu(user);
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Failed to update profile.");
            }
        });

        btnBack.addActionListener(e -> {
            frame.dispose();

            if (user.getRole().equals("Manager")) {
                new ManagerMenu(user);
            } else if (user.getRole().equals("CounterStaff")) {
                new CounterStaffMenu(user);
            } else if (user.getRole().equals("Technician")) {
                new TechnicianMenu(user);
            } else if (user.getRole().equals("Customer")) {
                new CustomerMenu(user);
            }
        });

        frame.setVisible(true);
    }

    private boolean updateProfile(int userId, String phone, String email, String password,
                                  String securityQuestion, String securityAnswer) {
        File file = new File("data/users.txt");
        ArrayList<String> lines = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] user = line.split("\\|");

                if (user.length >= 9 && Integer.parseInt(user[0]) == userId) {
                    lines.add(user[0] + "|" + user[1] + "|" + password + "|" +
                              phone + "|" + email + "|" + securityQuestion + "|" +
                              securityAnswer + "|" + user[7] + "|" + user[8]);
                } else {
                    lines.add(line);
                }
            }
        } catch (IOException e) {
            return false;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (String l : lines) {
                writer.write(l);
                writer.newLine();
            }
        } catch (IOException e) {
            return false;
        }

        return true;
    }
}