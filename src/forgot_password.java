import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;

public class forgot_password {

    public forgot_password() {
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

                File file = new File("data/users.txt");

                try {
                    BufferedReader reader = new BufferedReader(new FileReader(file));
                    String line;
                    boolean userFound = false;

                    while ((line = reader.readLine()) != null) {
                        String[] user = line.split(",");

                        if (user.length >= 7 && user[1].equalsIgnoreCase(username)) {
                            questionText.setText(user[5]);
                            userFound = true;
                            break;
                        }
                    }

                    reader.close();

                    if (!userFound) {
                        JOptionPane.showMessageDialog(frame, "Username not found.");
                        questionText.setText("");
                    }

                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(frame, "Error reading file.");
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

                File file = new File("data/users.txt");
                ArrayList<String> lines = new ArrayList<>();
                boolean userFound = false;
                boolean answerCorrect = false;

                try {
                    BufferedReader reader = new BufferedReader(new FileReader(file));
                    String line;

                    while ((line = reader.readLine()) != null) {
                        String[] user = line.split(",");

                        if (user.length >= 7 && user[1].equalsIgnoreCase(username)) {
                            userFound = true;

                            if (user[6].equalsIgnoreCase(answer)) {
                                answerCorrect = true;

                                lines.add(user[0] + "," + user[1] + "," + newPassword + "," +
                                          user[3] + "," + user[4] + "," + user[5] + "," + user[6]);
                            } else {
                                lines.add(line);
                            }
                        } else {
                            lines.add(line);
                        }
                    }
                    reader.close();

                    if (!userFound) {
                        JOptionPane.showMessageDialog(frame, "Username not found.");
                        return;
                    }

                    if (!answerCorrect) {
                        JOptionPane.showMessageDialog(frame, "Security answer is incorrect.");
                        return;
                    }

                    BufferedWriter writer = new BufferedWriter(new FileWriter(file));
                    for (String l : lines) {
                        writer.write(l);
                        writer.newLine();
                    }
                    writer.close();

                    JOptionPane.showMessageDialog(frame, "Password reset successful!");
                    frame.dispose();
                    login.main(null);

                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(frame, "Error updating password.");
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
}