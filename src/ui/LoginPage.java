package ui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import service.AuthService;
import model.User;

public class LoginPage {

    public LoginPage() {
        JFrame frame = new JFrame("Login Page");
        frame.setSize(350, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);

        JLabel userLabel = new JLabel("Username:");
        userLabel.setBounds(30, 30, 80, 25);
        frame.add(userLabel);

        JTextField userText = new JTextField();
        userText.setBounds(120, 30, 150, 25);
        frame.add(userText);

        JLabel passLabel = new JLabel("Password:");
        passLabel.setBounds(30, 70, 80, 25);
        frame.add(passLabel);

        JPasswordField passText = new JPasswordField();
        passText.setBounds(120, 70, 150, 25);
        frame.add(passText);

        JButton loginButton = new JButton("Login");
        loginButton.setBounds(120, 110, 100, 30);
        frame.add(loginButton);

        JButton registerButton = new JButton("New user? Register here");
        registerButton.setBounds(90, 150, 180, 25);
        registerButton.setBorderPainted(false);
        registerButton.setContentAreaFilled(false);
        registerButton.setForeground(java.awt.Color.BLUE);
        frame.add(registerButton);

        JButton forgotButton = new JButton("Forgot Password?");
        forgotButton.setBounds(105, 180, 140, 25);
        forgotButton.setBorderPainted(false);
        forgotButton.setContentAreaFilled(false);
        forgotButton.setForeground(java.awt.Color.RED);
        frame.add(forgotButton);

        forgotButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new ForgotPasswordPage();
            }
        });

        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new RegisterPage();
            }
        });

        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = userText.getText().trim();
                String password = new String(passText.getPassword()).trim();

                AuthService authService = new AuthService();
                User user = authService.login(username, password);

                if (user != null) {
                    String role = user.getRole();
                    String name = user.getUsername();

                    JOptionPane.showMessageDialog(frame, "Welcome " + name);

                    frame.dispose();

                    if (role.equals("Manager")) {
                        new ManagerMenu(name);
                    } else if (role.equals("CounterStaff")) {
                        new CounterStaffMenu(name);
                    } else if (role.equals("Technician")) {
                        new TechnicianMenu(name);
                    } else if (role.equals("Customer")) {
                        new CustomerMenu(name);
                    } else {
                        JOptionPane.showMessageDialog(null, "Unknown role!");
                    }

                } else {
                    JOptionPane.showMessageDialog(frame, "Invalid Username or Password!");
                }
            }
        });

        frame.setVisible(true);
    }
}