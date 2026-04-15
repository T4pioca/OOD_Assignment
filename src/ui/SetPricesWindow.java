package ui;

import model.User;

import javax.swing.*;
import java.io.*;

// GUI & Events: Swing components for price management (OODJ Principle)
public class SetPricesWindow {

    // Encapsulation: private file path
    private final String PRICES_FILE = "data/prices.txt";

    public SetPricesWindow(User manager) {

        JFrame frame = new JFrame("Set Service Prices");
        frame.setSize(450, 350);
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);

        // Back button
        JButton btnBack = new JButton("< Back");
        btnBack.setBounds(30, 10, 90, 25);
        frame.add(btnBack);

        JLabel title = new JLabel("Set Service Prices");
        title.setBounds(150, 10, 200, 25);
        title.setFont(title.getFont().deriveFont(14.0f));
        frame.add(title);

        // Normal Service fields
        JLabel lblNormal = new JLabel("Normal Service (1 hour):");
        lblNormal.setBounds(30, 60, 180, 25);
        frame.add(lblNormal);

        JLabel lblNormalPrice = new JLabel("Price (RM):");
        lblNormalPrice.setBounds(30, 90, 80, 25);
        frame.add(lblNormalPrice);

        JTextField tfNormalPrice = new JTextField();
        tfNormalPrice.setBounds(120, 90, 120, 25);
        frame.add(tfNormalPrice);

        // Major Service fields
        JLabel lblMajor = new JLabel("Major Service (3 hours):");
        lblMajor.setBounds(30, 140, 180, 25);
        frame.add(lblMajor);

        JLabel lblMajorPrice = new JLabel("Price (RM):");
        lblMajorPrice.setBounds(30, 170, 80, 25);
        frame.add(lblMajorPrice);

        JTextField tfMajorPrice = new JTextField();
        tfMajorPrice.setBounds(120, 170, 120, 25);
        frame.add(tfMajorPrice);

        // Save button
        JButton btnSave = new JButton("Save Prices");
        btnSave.setBounds(140, 230, 150, 35);
        frame.add(btnSave);

        // Load existing prices from file using BufferedReader
        loadPrices(tfNormalPrice, tfMajorPrice);

        // ActionListener: Save prices to file
        btnSave.addActionListener(e -> {
            // Robustness: try-catch for NumberFormatException and IOException
            try {
                String normalText = tfNormalPrice.getText().trim();
                String majorText = tfMajorPrice.getText().trim();

                if (normalText.isEmpty() || majorText.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please enter both prices.");
                    return;
                }

                // Exception Handling: NumberFormatException for invalid price input
                double normalPrice = Double.parseDouble(normalText);
                double majorPrice = Double.parseDouble(majorText);

                if (normalPrice < 0 || majorPrice < 0) {
                    JOptionPane.showMessageDialog(frame, "Prices cannot be negative.");
                    return;
                }

                // File I/O: FileWriter to save prices
                savePrices(normalPrice, majorPrice);

                JOptionPane.showMessageDialog(frame,
                    "Prices saved successfully!\n" +
                    "Normal Service: RM " + String.format("%.2f", normalPrice) + "\n" +
                    "Major Service: RM " + String.format("%.2f", majorPrice));

            } catch (NumberFormatException ex) {
                // Robustness: Handle invalid number input
                JOptionPane.showMessageDialog(frame,
                    "Invalid price format. Please enter valid numbers.");
            }
        });

        // ActionListener: Back to Manager Menu
        btnBack.addActionListener(e -> {
            frame.dispose();
            new ManagerMenu(manager);
        });

        frame.setVisible(true);
    }

    // File I/O: BufferedReader to load prices from file
    private void loadPrices(JTextField tfNormal, JTextField tfMajor) {
        File file = new File(PRICES_FILE);
        if (!file.exists()) {
            // Set default prices if file does not exist
            tfNormal.setText("50.00");
            tfMajor.setText("150.00");
            return;
        }

        // Exception Handling: try-with-resources for BufferedReader
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                // String.split() to parse pipe-delimited data
                String[] data = line.split("\\|");
                if (data.length >= 3) {
                    if (data[0].equalsIgnoreCase("Normal Service")) {
                        tfNormal.setText(data[2]);
                    } else if (data[0].equalsIgnoreCase("Major Service")) {
                        tfMajor.setText(data[2]);
                    }
                }
            }
        } catch (IOException e) {
            // Graceful error handling with defaults
            tfNormal.setText("50.00");
            tfMajor.setText("150.00");
        }
    }

    // File I/O: BufferedWriter/FileWriter to save prices
    private void savePrices(double normalPrice, double majorPrice) {
        File folder = new File("data");
        folder.mkdirs();

        // Exception Handling: try-with-resources for BufferedWriter
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(PRICES_FILE))) {
            bw.write("Normal Service|1|" + String.format("%.2f", normalPrice));
            bw.newLine();
            bw.write("Major Service|3|" + String.format("%.2f", majorPrice));
            bw.newLine();
        } catch (IOException e) {
            System.out.println("Error saving prices: " + e.getMessage());
        }
    }
}
