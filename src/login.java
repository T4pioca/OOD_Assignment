import javax.swing.*;
import java.awt.event.*;

public class login {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Login Page");
        frame.setSize(350, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

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

        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = userText.getText();
                String password = new String(passText.getPassword());

                if (username.equals("admin") && password.equals("1234")) {
                    JOptionPane.showMessageDialog(frame, "Login Successful!");
                } else {
                    JOptionPane.showMessageDialog(frame, "Invalid Username or Password!");
                }
            }
        });

        frame.setVisible(true);
    }
}