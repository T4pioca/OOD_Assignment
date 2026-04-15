package ui;

import model.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.*;
import java.util.ArrayList;

// GUI & Events: Swing components for reports viewing (OODJ Principle)
// Abstraction: Different report types filtered through abstraction methods
public class ViewReportsWindow {

    // Encapsulation: private file path
    private final String APPOINTMENTS_FILE = "data/Appointments.txt";

    public ViewReportsWindow(User manager) {

        JFrame frame = new JFrame("View Reports");
        frame.setSize(900, 520);
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);

        // Back button
        JButton btnBack = new JButton("< Back");
        btnBack.setBounds(30, 10, 90, 25);
        frame.add(btnBack);

        JLabel title = new JLabel("Analytical Reports");
        title.setBounds(370, 10, 200, 25);
        title.setFont(title.getFont().deriveFont(14.0f));
        frame.add(title);

        // Report type selector (Abstraction: different report views)
        JLabel lblReportType = new JLabel("Report Type:");
        lblReportType.setBounds(30, 45, 100, 25);
        frame.add(lblReportType);

        String[] reportTypes = {"All Appointments", "Service History (Completed)", "Payment History"};
        JComboBox<String> cmbReportType = new JComboBox<>(reportTypes);
        cmbReportType.setBounds(130, 45, 250, 25);
        frame.add(cmbReportType);

        JButton btnGenerate = new JButton("Generate Report");
        btnGenerate.setBounds(400, 45, 150, 25);
        frame.add(btnGenerate);

        // JTable for report display (GUI principle)
        DefaultTableModel tableModel = new DefaultTableModel();
        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(30, 85, 830, 360);
        frame.add(scrollPane);

        // Load all appointments initially
        ArrayList<String[]> allAppointments = loadAppointments();
        displayAllAppointments(tableModel, allAppointments);

        // ActionListener: Generate report based on selected type
        btnGenerate.addActionListener(e -> {
            String selectedType = cmbReportType.getSelectedItem().toString();

            // Abstraction: Different filter methods for different report types
            if (selectedType.equals("All Appointments")) {
                displayAllAppointments(tableModel, allAppointments);
            } else if (selectedType.equals("Service History (Completed)")) {
                displayServiceHistory(tableModel, allAppointments);
            } else if (selectedType.equals("Payment History")) {
                displayPaymentHistory(tableModel, allAppointments);
            }
        });

        // ActionListener: Back to Manager Menu
        btnBack.addActionListener(e -> {
            frame.dispose();
            new ManagerMenu(manager);
        });

        frame.setVisible(true);
    }

    // File I/O: BufferedReader with String.split() to parse appointments
    private ArrayList<String[]> loadAppointments() {
        ArrayList<String[]> appointments = new ArrayList<>();
        File file = new File(APPOINTMENTS_FILE);

        if (!file.exists()) {
            return appointments;
        }

        // Exception Handling: try-with-resources for BufferedReader
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                // String.split() to parse pipe-delimited data
                String[] data = line.split("\\|");
                if (data.length >= 10) {
                    appointments.add(data);
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading appointments: " + e.getMessage());
        }

        return appointments;
    }

    // Abstraction: Display all appointments report
    private void displayAllAppointments(DefaultTableModel model, ArrayList<String[]> appointments) {
        model.setRowCount(0);
        model.setColumnIdentifiers(new String[]{
            "ID", "Customer", "Service Type", "Technician", "Date", "Time",
            "Duration", "Plate", "Model", "Status"
        });

        for (String[] appt : appointments) {
            model.addRow(new Object[]{
                appt[0], appt[1], appt[2], appt[3], appt[4],
                appt[5], appt[6], appt[7], appt[8], appt[9]
            });
        }
    }

    // Abstraction: Filter and display only completed appointments (Service History)
    private void displayServiceHistory(DefaultTableModel model, ArrayList<String[]> appointments) {
        model.setRowCount(0);
        model.setColumnIdentifiers(new String[]{
            "ID", "Customer", "Service Type", "Technician", "Date", "Time",
            "Duration", "Vehicle", "Status"
        });

        for (String[] appt : appointments) {
            // Filter: Only show completed appointments
            if (appt[9].equalsIgnoreCase("Completed")) {
                model.addRow(new Object[]{
                    appt[0], appt[1], appt[2], appt[3], appt[4],
                    appt[5], appt[6], appt[8], appt[9]
                });
            }
        }

        if (model.getRowCount() == 0) {
            model.addRow(new Object[]{
                "No completed appointments found", "", "", "", "", "", "", "", ""
            });
        }
    }

    // Abstraction: Display payment history per appointment
    private void displayPaymentHistory(DefaultTableModel model, ArrayList<String[]> appointments) {
        model.setRowCount(0);
        model.setColumnIdentifiers(new String[]{
            "Appointment ID", "Customer", "Service Type", "Date",
            "Duration (hrs)", "Status", "Payment Status"
        });

        for (String[] appt : appointments) {
            // Determine payment status based on appointment status
            String paymentStatus = appt[9].equalsIgnoreCase("Completed") ? "Paid" : "Pending";

            model.addRow(new Object[]{
                appt[0], appt[1], appt[2], appt[4],
                appt[6], appt[9], paymentStatus
            });
        }
    }
}
