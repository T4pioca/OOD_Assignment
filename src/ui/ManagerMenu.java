package ui;

import javax.swing.*;
import model.User;

public class ManagerMenu {

    public ManagerMenu(User user) {

        JFrame frame = new JFrame("Manager Menu");
        frame.setSize(500, 380);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);

        // Welcome Label
        JLabel welcomeLabel = new JLabel("Welcome Manager, " + user.getUsername());
        welcomeLabel.setBounds(120, 30, 300, 25);
        frame.add(welcomeLabel);

        // Buttons
        JButton btnProfile = new JButton("Edit Profile");
        btnProfile.setBounds(150, 80, 200, 30);
        frame.add(btnProfile);
        
        JButton btnManageStaff = new JButton("Manage Staff (CRUD)");
        btnManageStaff.setBounds(150, 120, 200, 30);
        frame.add(btnManageStaff);

        JButton btnSetPrice = new JButton("Set Service Prices");
        btnSetPrice.setBounds(150, 160, 200, 30);
        frame.add(btnSetPrice);

        JButton btnFeedback = new JButton("View Feedbacks");
        btnFeedback.setBounds(150, 200, 200, 30);
        frame.add(btnFeedback);

        JButton btnReport = new JButton("View Reports");
        btnReport.setBounds(150, 240, 200, 30);
        frame.add(btnReport);

        JButton btnLogout = new JButton("Logout");
        btnLogout.setBounds(150, 280, 200, 30);
        frame.add(btnLogout);

        // ActionListener: Edit Profile
        btnProfile.addActionListener(e -> {
        	frame.dispose();
        	new EditProfile(user);
        });

        // ActionListener: Manage Staff → opens ManageStaffWindow
        btnManageStaff.addActionListener(e -> {
            frame.dispose();
            new ManageStaffWindow(user);
        });

        // ActionListener: Set Service Prices → opens SetPricesWindow
        btnSetPrice.addActionListener(e -> {
            frame.dispose();
            new SetPricesWindow(user);
        });

        // ActionListener: View Feedbacks → opens ViewFeedbacksWindow
        btnFeedback.addActionListener(e -> {
            frame.dispose();
            new ViewFeedbacksWindow(user);
        });

        // ActionListener: View Reports → opens ViewReportsWindow
        btnReport.addActionListener(e -> {
            frame.dispose();
            new ViewReportsWindow(user);
        });

        // Logout action
        btnLogout.addActionListener(e -> {
            frame.dispose();
            new LoginPage();
        });

        frame.setVisible(true);
    }
}