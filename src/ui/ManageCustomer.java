package ui;

import model.User;
import java.io.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.awt.Color;

public class ManageCustomer {

	private ArrayList<User> allUsers = new ArrayList<>();
    private ArrayList<User> list = new ArrayList<>();
    private DefaultTableModel model;
    private final String file = "data/users.txt";
    
    public ManageCustomer(User user) {

        JFrame f = new JFrame("Customer Management");
        f.setSize(800,420);
        f.setLayout(null);
        f.setLocationRelativeTo(null);
        
        JButton btnBack = new JButton("< Back");
        btnBack.setBounds(30, 10, 90, 25);
        f.add(btnBack);

        JLabel l1 = new JLabel("Username");
        l1.setBounds(30,40,100,25);
        f.add(l1);

        JTextField tfUsername = new JTextField();
        tfUsername.setBounds(150,40,200,25);
        tfUsername.getDocument().addDocumentListener(fillColorListener(tfUsername));
        f.add(tfUsername);

        JLabel l2 = new JLabel("Password");
        l2.setBounds(380,40,100,25);
        f.add(l2);

        JTextField tfPassword = new JTextField();
        tfPassword.setBounds(500,40,200,25);
        tfPassword.getDocument().addDocumentListener(fillColorListener(tfPassword));
        f.add(tfPassword);

        JLabel l3 = new JLabel("Phone");
        l3.setBounds(30,80,100,25);
        f.add(l3);

        JTextField tfPhone = new JTextField();
        tfPhone.setBounds(150,80,200,25);
        tfPhone.getDocument().addDocumentListener(fillColorListener(tfPhone));
        f.add(tfPhone);

        JLabel l4 = new JLabel("Email");
        l4.setBounds(380,80,100,25);
        f.add(l4);

        JTextField tfEmail = new JTextField();
        tfEmail.setBounds(500,80,200,25);
        tfEmail.getDocument().addDocumentListener(fillColorListener(tfEmail));
        f.add(tfEmail);

        JButton addBtn = new JButton("Add");
        addBtn.setBounds(30, 120, 90, 30);
        addBtn.setBackground(new Color(102, 204, 255));
        addBtn.setForeground(Color.BLACK);
        f.add(addBtn);

        JButton updBtn = new JButton("Update");
        updBtn.setBounds(130, 120, 90, 30);
        updBtn.setBackground(new Color(255, 204, 102));
        updBtn.setForeground(Color.BLACK);
        f.add(updBtn);

        JButton activateBtn = new JButton("Activate");
        activateBtn.setBounds(230, 120, 100, 30);
        activateBtn.setBackground(new Color(51, 204, 51));
        activateBtn.setForeground(Color.BLACK);
        f.add(activateBtn);

        JButton deactivateBtn = new JButton("Deactivate");
        deactivateBtn.setBounds(340, 120, 110, 30);
        deactivateBtn.setBackground(new Color(201, 51, 0));
        deactivateBtn.setForeground(Color.BLACK);
        f.add(deactivateBtn);
        
        JButton clearBtn = new JButton("Clear");
        clearBtn.setBounds(560, 120, 90, 30); 
        f.add(clearBtn);
        
        model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{"ID","Username","Phone","Email","Status"});

        JTable table = new JTable(model);
        JScrollPane sp = new JScrollPane(table);
        sp.setBounds(30,160,720,150);
        f.add(sp);

        loadCustomers();

        addBtn.addActionListener(e -> {
            String id = getNextId();
            User customer = new User(
                    id,
                    tfUsername.getText(),
                    tfPassword.getText(),
                    tfPhone.getText(),
                    tfEmail.getText(),
                    "N/A",
                    "N/A",
                    "Customer",
                    "Active"
            );
            list.add(customer);
            model.addRow(new Object[]{
                    customer.getId(),
                    customer.getUsername(),
                    customer.getPhone(),
                    customer.getEmail(),
                    customer.getStatus()
            });
            saveCustomers();
        });

        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int i = table.getSelectedRow();
                tfUsername.setText(model.getValueAt(i,1).toString());
                tfPassword.setText(list.get(i).getPassword());
                tfPhone.setText(model.getValueAt(i,2).toString());
                tfEmail.setText(model.getValueAt(i,3).toString());
            }
        });

        updBtn.addActionListener(e -> {
            int i = table.getSelectedRow();
            if(i < 0) return;
            User u = list.get(i);
            u.setUsername(tfUsername.getText());
            u.setPassword(tfPassword.getText());
            u.setPhone(tfPhone.getText());
            u.setEmail(tfEmail.getText());

            model.setValueAt(tfUsername.getText(), i, 1);
            model.setValueAt(tfPhone.getText(), i, 2);
            model.setValueAt(tfEmail.getText(), i, 3);

            saveCustomers();
        });

        activateBtn.addActionListener(e -> {
            int i = table.getSelectedRow();
            if(i < 0) return; 

            User u = list.get(i);
            u.setStatus("Active");
            model.setValueAt("Active", i, 4); 
            saveCustomers();
        });

        deactivateBtn.addActionListener(e -> {
            int i = table.getSelectedRow();
            if(i < 0) return; 

            User u = list.get(i);
            u.setStatus("Inactive");
            model.setValueAt("Inactive", i, 4); 
            saveCustomers();
        });

        f.setVisible(true);
        
        activateBtn.setEnabled(false);
        deactivateBtn.setEnabled(false);

        table.getSelectionModel().addListSelectionListener(e -> {
            int i = table.getSelectedRow();
            if(i >= 0) {
                User u = list.get(i);
                activateBtn.setEnabled(!u.getStatus().equalsIgnoreCase("Active"));
                deactivateBtn.setEnabled(!u.getStatus().equalsIgnoreCase("Inactive"));
            }
        });
        
        btnBack.addActionListener(e -> {
            f.dispose();

            if (user.getRole().equals("Manager")) {
                new ManagerMenu(user);
            } else if (user.getRole().equals("CounterStaff")) {
                new CounterStaffMenu(user);
            } else if (user.getRole().equals("Technician")) {
                new TechnicianMenu(user);
            } else if (user.getRole().equals("Customer")) {
                new CustomerMenu(user);
            }
        });
        
        clearBtn.addActionListener(e -> {
            tfUsername.setText("");
            tfPassword.setText("");
            tfPhone.setText("");
            tfEmail.setText("");
            table.clearSelection(); 
        });

        f.setVisible(true);
    }
    
    DocumentListener fillColorListener(JTextField tf) {
        return new DocumentListener() {
            void update() {
                if(tf.getText().isEmpty()) {
                    tf.setBackground(Color.WHITE);
                } else {
                    tf.setBackground(new Color(255, 255, 204)); 
                }
            }
            public void insertUpdate(DocumentEvent e) { update(); }
            public void removeUpdate(DocumentEvent e) { update(); }
            public void changedUpdate(DocumentEvent e) { update(); }
        };
    }

    private void loadCustomers() {
        allUsers.clear();
        list.clear(); 
        model.setRowCount(0); 

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while((line = br.readLine()) != null) {
                String[] data = line.split("\\|");
                User u = new User(
                        data[0],
                        data[1],
                        data[2],
                        data[3],
                        data[4],
                        data[5],
                        data[6],
                        data[7],
                        data[8]
                );
                allUsers.add(u);

                if(u.getRole().equalsIgnoreCase("Customer")) {
                    list.add(u); // only for table
                    model.addRow(new Object[]{
                            u.getId(),
                            u.getUsername(),
                            u.getPhone(),
                            u.getEmail(),
                            u.getStatus()
                    });
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveCustomers() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            for(User u : allUsers) {
                // Check if this user is a customer in the table list, update info
                if(u.getRole().equalsIgnoreCase("Customer")) {
                    for(User updatedCustomer : list) {
                        if(u.getId().equals(updatedCustomer.getId())) {
                            u.setUsername(updatedCustomer.getUsername());
                            u.setPassword(updatedCustomer.getPassword());
                            u.setPhone(updatedCustomer.getPhone());
                            u.setEmail(updatedCustomer.getEmail());
                            u.setStatus(updatedCustomer.getStatus());
                            break;
                        }
                    }
                }

                // Write the user to file
                String line = u.getId() + "|" +
                        u.getUsername() + "|" +
                        u.getPassword() + "|" +
                        u.getPhone() + "|" +
                        u.getEmail() + "|" +
                        u.getSecurityQuestion() + "|" +
                        u.getSecurityAnswer() + "|" +
                        u.getRole() + "|" +
                        u.getStatus();
                bw.write(line);
                bw.newLine();
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    private String getNextId() {

        int max = 0;

        for(User u : allUsers) {

            if(u.getRole().equalsIgnoreCase("Customer")) {

                String id = u.getId(); 

                try {
                    int num = Integer.parseInt(id.substring(1));

                    if(num > max)
                        max = num;

                } catch(Exception e) {}
            }
        }

        return String.format("C%03d", max + 1);
    }
}