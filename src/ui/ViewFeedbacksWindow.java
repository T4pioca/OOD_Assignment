package ui;

import model.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.*;
import java.util.ArrayList;

// GUI & Events: Swing components for feedback viewing (OODJ Principle)
public class ViewFeedbacksWindow {

    // Encapsulation: private file path
    private final String FEEDBACK_FILE = "data/feedback.txt";

    public ViewFeedbacksWindow(User manager) {

        JFrame frame = new JFrame("View Feedbacks");
        frame.setSize(800, 480);
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);

        // Back button
        JButton btnBack = new JButton("< Back");
        btnBack.setBounds(30, 10, 90, 25);
        frame.add(btnBack);

        JLabel title = new JLabel("Customer & Staff Feedbacks");
        title.setBounds(300, 10, 250, 25);
        title.setFont(title.getFont().deriveFont(14.0f));
        frame.add(title);

        // Filter section
        JLabel lblFilter = new JLabel("Filter by:");
        lblFilter.setBounds(30, 45, 60, 25);
        frame.add(lblFilter);

        JTextField tfFilter = new JTextField();
        tfFilter.setBounds(100, 45, 200, 25);
        frame.add(tfFilter);

        JButton btnFilter = new JButton("Filter");
        btnFilter.setBounds(310, 45, 80, 25);
        frame.add(btnFilter);

        JButton btnShowAll = new JButton("Show All");
        btnShowAll.setBounds(400, 45, 100, 25);
        frame.add(btnShowAll);

        // JTable for feedbacks (GUI principle)
        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(
            new String[]{"Appointment ID", "From", "Rating", "Comment", "Date"}
        );

        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(30, 85, 730, 330);
        frame.add(scrollPane);

        // Load all feedbacks initially
        ArrayList<String[]> allFeedbacks = loadFeedbacks();
        displayFeedbacks(tableModel, allFeedbacks);

        // ActionListener: Filter feedbacks by appointment ID or name
        btnFilter.addActionListener(e -> {
            String filterText = tfFilter.getText().trim().toLowerCase();
            if (filterText.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please enter a filter keyword.");
                return;
            }

            // Abstraction: Filter method to search feedbacks
            ArrayList<String[]> filtered = new ArrayList<>();
            for (String[] feedback : allFeedbacks) {
                // Search across appointment ID and from fields
                if (feedback[0].toLowerCase().contains(filterText) ||
                    feedback[1].toLowerCase().contains(filterText)) {
                    filtered.add(feedback);
                }
            }

            displayFeedbacks(tableModel, filtered);

            if (filtered.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "No feedbacks found for: " + filterText);
            }
        });

        // ActionListener: Show all feedbacks
        btnShowAll.addActionListener(e -> {
            tfFilter.setText("");
            displayFeedbacks(tableModel, allFeedbacks);
        });

        // ActionListener: Back to Manager Menu
        btnBack.addActionListener(e -> {
            frame.dispose();
            new ManagerMenu(manager);
        });

        frame.setVisible(true);
    }

    // File I/O: BufferedReader with String.split() to parse feedback data
    private ArrayList<String[]> loadFeedbacks() {
        ArrayList<String[]> feedbacks = new ArrayList<>();
        File file = new File(FEEDBACK_FILE);

        if (!file.exists()) {
            return feedbacks;
        }

        // Exception Handling: try-with-resources for BufferedReader
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                // String.split() to parse pipe-delimited feedback data
                String[] data = line.split("\\|");
                if (data.length >= 5) {
                    feedbacks.add(data);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading feedbacks: " + e.getMessage());
        }

        return feedbacks;
    }

    // Abstraction: Separate method to display feedbacks in JTable
    private void displayFeedbacks(DefaultTableModel model, ArrayList<String[]> feedbacks) {
        model.setRowCount(0);
        for (String[] feedback : feedbacks) {
            model.addRow(new Object[]{
                feedback[0],  // Appointment ID
                feedback[1],  // From
                feedback[2],  // Rating
                feedback[3],  // Comment
                feedback[4]   // Date
            });
        }
    }
}
