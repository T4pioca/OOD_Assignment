package ui;

import javax.swing.*;

public class TechnicianMenu {

    public TechnicianMenu(String username) {

        JFrame frame = new JFrame("Technician Menu");
        frame.setSize(450, 350);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);

        // Welcome Label
        JLabel welcomeLabel = new JLabel("Welcome Technician, " + username);
        welcomeLabel.setBounds(100, 30, 300, 25);
        frame.add(welcomeLabel);

        // Buttons
        JButton btnProfile = new JButton("Edit Profile");
        btnProfile.setBounds(130, 80, 180, 30);
        frame.add(btnProfile);

        JButton btnAppointments = new JButton("View Appointments");
        btnAppointments.setBounds(130, 120, 180, 30);
        frame.add(btnAppointments);

        JButton btnComplete = new JButton("Mark as Completed");
        btnComplete.setBounds(130, 160, 180, 30);
        frame.add(btnComplete);

        JButton btnFeedback = new JButton("Provide Feedback");
        btnFeedback.setBounds(130, 200, 180, 30);
        frame.add(btnFeedback);

        JButton btnLogout = new JButton("Logout");
        btnLogout.setBounds(130, 240, 180, 30);
        frame.add(btnLogout);

        // Logout action
        btnLogout.addActionListener(e -> {
            frame.dispose();
            new LoginPage();
        });

        frame.setVisible(true);
    }
}