import javax.swing.*;
import java.awt.event.*;
import java.io.*;

public class register_user {

    public register_user() {
        JFrame frame = new JFrame("Register Page");
        frame.setSize(450, 480);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);

        JLabel titleLabel = new JLabel("User Registration");
        titleLabel.setBounds(160, 20, 150, 25);
        frame.add(titleLabel);

        JLabel userLabel = new JLabel("Username:");
        userLabel.setBounds(50, 60, 100, 25);
        frame.add(userLabel);

        JTextField userText = new JTextField();
        userText.setBounds(170, 60, 200, 25);
        frame.add(userText);

        JLabel passLabel = new JLabel("Password:");
        passLabel.setBounds(50, 100, 100, 25);
        frame.add(passLabel);

        JPasswordField passText = new JPasswordField();
        passText.setBounds(170, 100, 200, 25);
        frame.add(passText);

        JLabel confirmLabel = new JLabel("Confirm Password:");
        confirmLabel.setBounds(50, 140, 120, 25);
        frame.add(confirmLabel);

        JPasswordField confirmText = new JPasswordField();
        confirmText.setBounds(170, 140, 200, 25);
        frame.add(confirmText);

        JLabel phoneLabel = new JLabel("Phone Number:");
        phoneLabel.setBounds(50, 180, 100, 25);
        frame.add(phoneLabel);

        JTextField phoneText = new JTextField();
        phoneText.setBounds(170, 180, 200, 25);
        frame.add(phoneText);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(50, 220, 100, 25);
        frame.add(emailLabel);

        JTextField emailText = new JTextField();
        emailText.setBounds(170, 220, 200, 25);
        frame.add(emailText);
        
        JLabel questionLabel = new JLabel("Security Question:");
        questionLabel.setBounds(50, 260, 120, 25);
        frame.add(questionLabel);

        String[] questions = {
                "What is your favourite color?",
                "What is your pet's name?",
                "What city were you born in?",
                "What is your favourite food?"
        };

        JComboBox<String> questionBox = new JComboBox<>(questions);
        questionBox.setBounds(170, 260, 200, 25);
        frame.add(questionBox);

        JLabel answerLabel = new JLabel("Answer:");
        answerLabel.setBounds(50, 300, 120, 25);
        frame.add(answerLabel);

        JTextField answerText = new JTextField();
        answerText.setBounds(170, 300, 200, 25);
        frame.add(answerText);

        JButton registerButton = new JButton("Register");
        registerButton.setBounds(110, 360, 100, 30);
        frame.add(registerButton);

        JButton backButton = new JButton("Back to Login");
        backButton.setBounds(230, 360, 130, 30);
        frame.add(backButton);

        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = userText.getText().trim();
                String password = new String(passText.getPassword()).trim();
                String confirmPassword = new String(confirmText.getPassword()).trim();
                String phone = phoneText.getText().trim();
                String email = emailText.getText().trim();
                String securityQuestion = (String) questionBox.getSelectedItem();
                String securityAnswer = answerText.getText().trim();

                if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()
                        || phone.isEmpty() || email.isEmpty() || securityAnswer.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please fill in all fields.");
                    return;
                }

                if (!password.equals(confirmPassword)) {
                    JOptionPane.showMessageDialog(frame, "Passwords do not match.");
                    return;
                }

                File folder = new File("data");
                folder.mkdirs();

                File file = new File("data/users.txt");

                try {
                    if (!file.exists()) {
                        file.createNewFile();
                    }

                    if (isUsernameExists(username, file)) {
                        JOptionPane.showMessageDialog(frame, "Username already exists.");
                        return;
                    }

                    int newId = getNextUserId(file);

                    FileWriter writer = new FileWriter(file, true);
                    writer.write(newId + "," + username + "," + password + "," + phone + "," + email + "," + securityQuestion + "," + securityAnswer + "\n");
                    writer.close();

                    JOptionPane.showMessageDialog(frame, "Registration successful!");

                    frame.dispose();
                    login.main(null);

                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(frame, "Error saving user data.");
                }
            }
        });

        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                login.main(null);
            }
        });

        frame.setVisible(true);
    }

    private boolean isUsernameExists(String username, File file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;

        while ((line = reader.readLine()) != null) {
            String[] user = line.split(",");
            if (user.length >= 2 && user[1].equalsIgnoreCase(username)) {
                reader.close();
                return true;
            }
        }

        reader.close();
        return false;
    }

    private int getNextUserId(File file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;
        int lastId = 0;

        while ((line = reader.readLine()) != null) {
            String[] user = line.split(",");
            if (user.length >= 1) {
                lastId = Integer.parseInt(user[0]);
            }
        }

        reader.close();
        return lastId + 1;
    }
}