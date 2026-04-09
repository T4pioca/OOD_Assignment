package ui;

import model.User;
import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.awt.Color;

public class CreateAppointment {

    private final String customerFile = "data/users.txt";
    private final String technicianFile = "data/users.txt";
    private final String appointmentFile = "data/appointments.txt";

    private JComboBox<String> customerBox;
    private JComboBox<String> serviceBox;
    private JComboBox<String> technicianBox;
    private JComboBox<String> timeBox;
    private JTextField dateField;
    private JTextField plateField;  // Car plate
    private JTextField modelField;  // Vehicle model
    private JLabel durationLabel;

    public CreateAppointment(User user) {

        JFrame f = new JFrame("Create Appointment");
        f.setSize(420, 450); // increased height for new field
        f.setLayout(null);
        f.setLocationRelativeTo(null);

        JLabel title = new JLabel("Create Appointment");
        title.setBounds(150, 10, 200, 25);
        f.add(title);

        // Customer
        JLabel l1 = new JLabel("Customer:");
        l1.setBounds(30, 50, 100, 25);
        f.add(l1);

        customerBox = new JComboBox<>(loadCustomers());
        customerBox.setBounds(150, 50, 200, 25);
        f.add(customerBox);

        // Service Type
        JLabel l2 = new JLabel("Service Type:");
        l2.setBounds(30, 80, 100, 25);
        f.add(l2);

        String[] services = {"Normal Service", "Major Service"};
        serviceBox = new JComboBox<>(services);
        serviceBox.setBounds(150, 80, 200, 25);
        f.add(serviceBox);

        // Technician
        JLabel l3 = new JLabel("Technician:");
        l3.setBounds(30, 110, 100, 25);
        f.add(l3);

        technicianBox = new JComboBox<>(loadTechnicians());
        technicianBox.setBounds(150, 110, 200, 25);
        f.add(technicianBox);

        // Date
        JLabel l4 = new JLabel("Date:");
        l4.setBounds(30, 140, 100, 25);
        f.add(l4);

        dateField = new JTextField("2026-04-10");
        dateField.setBounds(150, 140, 200, 25);
        f.add(dateField);

        // Start Time
        JLabel l5 = new JLabel("Start Time:");
        l5.setBounds(30, 170, 100, 25);
        f.add(l5);

        String[] times = {"09:00","10:00","11:00","12:00","14:00","15:00","16:00"};
        timeBox = new JComboBox<>(times);
        timeBox.setBounds(150, 170, 200, 25);
        f.add(timeBox);

        // Duration
        JLabel l6 = new JLabel("Duration:");
        l6.setBounds(30, 200, 100, 25);
        f.add(l6);

        durationLabel = new JLabel("1 hour");
        durationLabel.setBounds(150, 200, 200, 25);
        f.add(durationLabel);

        // Car Plate
        JLabel l7 = new JLabel("Car Plate:");
        l7.setBounds(30, 230, 100, 25);
        f.add(l7);

        plateField = new JTextField();
        plateField.setBounds(150, 230, 200, 25);
        f.add(plateField);

        // Vehicle Model
        JLabel l8 = new JLabel("Vehicle Model:");
        l8.setBounds(30, 260, 100, 25);
        f.add(l8);

        modelField = new JTextField();
        modelField.setBounds(150, 260, 200, 25);
        f.add(modelField);

        // Buttons
        JButton assignBtn = new JButton("Assign Appointment");
        assignBtn.setBounds(130, 310, 150, 30);
        f.add(assignBtn);

        JButton backBtn = new JButton("< Back");
        backBtn.setBounds(10, 10, 90, 25);
        f.add(backBtn);

        // Service duration listener
        serviceBox.addActionListener(e -> {
            String service = serviceBox.getSelectedItem().toString();
            durationLabel.setText(service.equals("Normal Service") ? "1 hour" : "3 hours");
        });

        // Assign Appointment
        assignBtn.addActionListener(e -> {

            // Get values
            String customer = (customerBox.getSelectedItem() != null) ? customerBox.getSelectedItem().toString() : "";
            String service = (serviceBox.getSelectedItem() != null) ? serviceBox.getSelectedItem().toString() : "";
            String technician = (technicianBox.getSelectedItem() != null) ? technicianBox.getSelectedItem().toString() : "";
            String date = dateField.getText().trim();
            String plate = plateField.getText().trim();
            String model = modelField.getText().trim();

            boolean valid = true;

            // Reset borders
            customerBox.setBorder(UIManager.getLookAndFeel().getDefaults().getBorder("ComboBox.border"));
            serviceBox.setBorder(UIManager.getLookAndFeel().getDefaults().getBorder("ComboBox.border"));
            technicianBox.setBorder(UIManager.getLookAndFeel().getDefaults().getBorder("ComboBox.border"));
            dateField.setBorder(UIManager.getLookAndFeel().getDefaults().getBorder("TextField.border"));
            plateField.setBorder(UIManager.getLookAndFeel().getDefaults().getBorder("TextField.border"));
            modelField.setBorder(UIManager.getLookAndFeel().getDefaults().getBorder("TextField.border"));

            // Validate fields
            if(customer.isEmpty()){ customerBox.setBorder(BorderFactory.createLineBorder(Color.RED)); valid = false; }
            if(service.isEmpty()){ serviceBox.setBorder(BorderFactory.createLineBorder(Color.RED)); valid = false; }
            if(technician.isEmpty()){ technicianBox.setBorder(BorderFactory.createLineBorder(Color.RED)); valid = false; }
            if(date.isEmpty()){ dateField.setBorder(BorderFactory.createLineBorder(Color.RED)); valid = false; }
            if(plate.isEmpty()){ plateField.setBorder(BorderFactory.createLineBorder(Color.RED)); valid = false; }
            if(model.isEmpty()){ modelField.setBorder(BorderFactory.createLineBorder(Color.RED)); valid = false; }

            if(!valid){
                JOptionPane.showMessageDialog(f, "Please fill in all required fields!");
                return; // Stop if validation fails
            }

            // Duration
            int duration = service.equals("Normal Service") ? 1 : 3;
            String appointmentID = generateAppointmentID();

            // Save appointment
            saveAppointment(appointmentID, customer, service, technician, date, timeBox.getSelectedItem().toString(), duration, plate, model);

            // Success message
            JOptionPane.showMessageDialog(f, "Appointment Created Successfully!");

            // Close current window and return to customer staff menu
            f.dispose();
            new CounterStaffMenu(user); // assuming 'user' is the logged-in staff
        });

        // Back button
        backBtn.addActionListener(e -> {
            f.dispose();
            new CounterStaffMenu(user);
        });

        f.setVisible(true);
    }

    private String[] loadCustomers() {
        ArrayList<String> list = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(customerFile))) {
            String line;
            while((line = br.readLine()) != null) {
                String[] data = line.split("\\|");
                if(data[7].equalsIgnoreCase("Customer") && data[8].equalsIgnoreCase("Active")) {
                    list.add(data[1]); // Only show name
                }
            }
        } catch (Exception e) {}
        return list.toArray(new String[0]);
    }

    private String[] loadTechnicians() {
        ArrayList<String> list = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(technicianFile))) {
            String line;
            while((line = br.readLine()) != null) {
                String[] data = line.split("\\|");
                if(data[7].equalsIgnoreCase("Technician") && data[8].equalsIgnoreCase("Active")) {
                    list.add(data[1]); // Only show name
                }
            }
        } catch (Exception e) {}
        return list.toArray(new String[0]);
    }

    private String generateAppointmentID() {
        int max = 0;
        File file = new File(appointmentFile);
        if (!file.exists()) return "A001";

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split("\\|");
                int num = Integer.parseInt(data[0].substring(1));
                if (num > max) max = num;
            }
        } catch (Exception e) { e.printStackTrace(); }

        return String.format("A%03d", max + 1);
    }

    private void saveAppointment(String id,
                                 String customer,
                                 String service,
                                 String technician,
                                 String date,
                                 String time,
                                 int duration,
                                 String plate,
                                 String model) {

        try {
            File folder = new File("data");
            folder.mkdirs();

            try (BufferedWriter bw = new BufferedWriter(new FileWriter(appointmentFile, true))) {
                bw.write(id + "|" +
                        customer + "|" +
                        service + "|" +
                        technician + "|" +
                        date + "|" +
                        time + "|" +
                        duration + "|" +
                        plate + "|" +
                        model + "|" +
                        "Scheduled");
                bw.newLine();
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error saving appointment");
        }
    }
}