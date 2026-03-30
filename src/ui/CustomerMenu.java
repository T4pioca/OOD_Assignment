package ui;

import javax.swing.*;
import model.User;

public class CustomerMenu {

    public CustomerMenu(User user) {

        JFrame frame = new JFrame("Customer Menu");
        frame.setSize(450, 350);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);

        // Welcome Label
        JLabel welcomeLabel = new JLabel("Welcome Customer, " + user.getUsername());
        welcomeLabel.setBounds(100, 30, 300, 25);
        frame.add(welcomeLabel);

        // Buttons
        JButton btnProfile = new JButton("Edit Profile");
        btnProfile.setBounds(130, 80, 180, 30);
        frame.add(btnProfile);

        JButton btnHistory = new JButton("Service History");
        btnHistory.setBounds(130, 120, 180, 30);
        frame.add(btnHistory);

        JButton btnPayment = new JButton("Payment History");
        btnPayment.setBounds(130, 160, 180, 30);
        frame.add(btnPayment);

        JButton btnFeedback = new JButton("View Feedback");
        btnFeedback.setBounds(130, 200, 180, 30);
        frame.add(btnFeedback);

        JButton btnLogout = new JButton("Logout");
        btnLogout.setBounds(130, 240, 180, 30);
        frame.add(btnLogout);

        btnProfile.addActionListener(e -> {
        	frame.dispose();
        	new EditProfile(user);
        });
        // Logout action
        btnLogout.addActionListener(e -> {
            frame.dispose();
            new LoginPage();
        });

        frame.setVisible(true);
    }
}