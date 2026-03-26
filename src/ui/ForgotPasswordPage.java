package ui;

import javax.swing.*;
import java.awt.event.*;
import service.AuthService;

public class ForgotPasswordPage {

    public ForgotPasswordPage() {
        JFrame frame = new JFrame("Forgot Password");
        frame.setSize(450, 330);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);

        JLabel userLabel = new JLabel("Username:");
        userLabel.setBounds(40, 30, 120, 25);
        frame.add(userLabel);

        JTextField userText = new JTextField();
        userText.setBounds(170, 30, 200, 25);
        frame.add(userText);

        JLabel questionLabel = new JLabel("Security Question:");
        questionLabel.setBounds(40, 70, 120, 25);
        frame.add(questionLabel);

        JTextField questionText = new JTextField();
        questionText.setBounds(170, 70, 200, 25);
        questionText.setEditable(false);
        frame.add(questionText);

        JButton findButton = new JButton("Find Question");
        findButton.setBounds(170, 105, 130, 25);
        frame.add(findButton);

        JLabel answerLabel = new JLabel("Security Answer:");
        answerLabel.setBounds(40, 145, 120, 25);
        frame.add(answerLabel);

        JTextField answerText = new JTextField();
        answerText.setBounds(170, 145, 200, 25);
        frame.add(answerText);

        JLabel passLabel = new JLabel("New Password:");
        passLabel.setBounds(40, 185, 120, 25);
        frame.add(passLabel);

        JPasswordField passText = new JPasswordField();
        passText.setBounds(170, 185, 200, 25);
        frame.add(passText);

        JButton resetButton = new JButton("Reset Password");
        resetButton.setBounds(90, 235, 130, 30);
        frame.add(resetButton);

        JButton backButton = new JButton("Back to Login");
        backButton.setBounds(240, 235, 130, 30);
        frame.add(backButton);

        findButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = userText.getText().trim();

                if (username.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please enter username.");
                    return;
                }

                AuthService authService = new AuthService();
                String question = authService.getSecurityQuestion(username);

                if (question == null) {
                    JOptionPane.showMessageDialog(frame, "Username not found.");
                    questionText.setText("");
                } else {
                    questionText.setText(question);
                }
            }
        });

        resetButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = userText.getText().trim();
                String answer = answerText.getText().trim();
                String newPassword = new String(passText.getPassword()).trim();

                if (username.isEmpty() || answer.isEmpty() || newPassword.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please fill in all fields.");
                    return;
                }

                AuthService authService = new AuthService();
                boolean success = authService.resetPassword(username, answer, newPassword);

                if (success) {
                    JOptionPane.showMessageDialog(frame, "Password reset successful!");
                    frame.dispose();
                    new LoginPage();
                } else {
                    JOptionPane.showMessageDialog(frame, "Username not found or answer incorrect.");
                }
            }
        });

        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new LoginPage();
            }
        });

        frame.setVisible(true);
    }
}