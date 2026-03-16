import javax.swing.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class login {
    public static void main(String[] args) {

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
                new forgot_password();
            }
        });
        
        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();        
                new register_user();   
            }
        });

        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                String username = userText.getText();
                String password = new String(passText.getPassword());

                boolean loginSuccess = false;

                try {
                    BufferedReader reader = new BufferedReader(new FileReader("data/users.txt"));
                    String line;

                    while ((line = reader.readLine()) != null) {
                        String[] user = line.split(",");

                        if (user.length >= 3 && user[1].equals(username) && user[2].equals(password)) {
                            loginSuccess = true;
                            break;
                        }
                    }

                    reader.close();

                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(frame, "Error reading file");
                }

                if (loginSuccess) {
                    JOptionPane.showMessageDialog(frame, "Login Successful!");
                } else {
                    JOptionPane.showMessageDialog(frame, "Invalid Username or Password!");
                }
            }
        });

        frame.setVisible(true);
    }
}