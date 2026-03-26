package ui;

import javax.swing.*;

public class CounterStaffMenu {

    public CounterStaffMenu(String username) {

        JFrame frame = new JFrame("Counter Staff Menu");
        frame.setSize(450, 350);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);

        // Welcome Label
        JLabel welcomeLabel = new JLabel("Welcome Counter Staff, " + username);
        welcomeLabel.setBounds(90, 30, 300, 25);
        frame.add(welcomeLabel);

        // Buttons
        JButton btnProfile = new JButton("Edit Profile");
        btnProfile.setBounds(130, 80, 180, 30);
        frame.add(btnProfile);

        JButton btnCustomer = new JButton("Manage Customers");
        btnCustomer.setBounds(130, 120, 180, 30);
        frame.add(btnCustomer);

        JButton btnAppointment = new JButton("Create Appointment");
        btnAppointment.setBounds(130, 160, 180, 30);
        frame.add(btnAppointment);

        JButton btnPayment = new JButton("Payment & Receipt");
        btnPayment.setBounds(130, 200, 180, 30);
        frame.add(btnPayment);

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