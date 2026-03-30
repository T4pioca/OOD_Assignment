package service;

import java.io.*;
import java.util.ArrayList;
import model.User;

public class AuthService {

    public User login(String username, String password) {
        File file = new File("data/users.txt");

        if (!file.exists()) {
            return null;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader("data/users.txt"))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] user = line.split("\\|");

                if (user.length >= 9 &&
                    user[1].equals(username) &&
                    user[2].equals(password) &&
                    user[8].equalsIgnoreCase("Active")) {

                	return new User(
                    	Integer.parseInt(user[0]),
                    	user[1],
                    	user[2],
                    	user[3],
                    	user[4],
                    	user[5],
                    	user[6],
                    	user[7],
                    	user[8]
                	);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file");
        }

        return null;
    }

    public boolean register(String username, String password, String phone, String email,
                            String securityQuestion, String securityAnswer, String role) {
        File folder = new File("data");
        folder.mkdirs();

        File file = new File("data/users.txt");

        try {
            if (!file.exists()) {
                file.createNewFile();
            }

            if (isUsernameExists(username, file)) {
                return false;
            }

            int newId = getNextUserId(file);

            try (FileWriter writer = new FileWriter(file, true)) {
                writer.write(newId + "|" + username + "|" + password + "|" + phone + "|" +
                             email + "|" + securityQuestion + "|" + securityAnswer + "|" + role + "|Active\n");
            }

            return true;

        } catch (IOException e) {
            return false;
        }
    }

    private boolean isUsernameExists(String username, File file) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] user = line.split("\\|");
                if (user.length >= 2 && user[1].equalsIgnoreCase(username)) {
                    return true;
                }
            }
        }
        return false;
    }

    private int getNextUserId(File file) throws IOException {
        int lastId = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] user = line.split("\\|");
                if (user.length >= 1) {
                    lastId = Integer.parseInt(user[0]);
                }
            }
        }

        return lastId + 1;
    }
    
    public String getSecurityQuestion(String username) {
        File file = new File("data/users.txt");

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] user = line.split("\\|");

                if (user.length >= 7 && user[1].equalsIgnoreCase(username)) {
                    return user[5];
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file.");
        }

        return null;
    }

    public boolean resetPassword(String username, String answer, String newPassword) {
        File file = new File("data/users.txt");
        ArrayList<String> lines = new ArrayList<>();
        boolean userFound = false;
        boolean answerCorrect = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] user = line.split("\\|");

                if (user.length >= 7 && user[1].equalsIgnoreCase(username)) {
                    userFound = true;

                    if (user[6].equalsIgnoreCase(answer)) {
                        answerCorrect = true;

                        lines.add(user[0] + "|" + user[1] + "|" + newPassword + "|" +
                                  user[3] + "|" + user[4] + "|" + user[5] + "|" + user[6] + "|" + user[7] + "|" + user[8]);
                    } else {
                        lines.add(line);
                    }
                } else {
                    lines.add(line);
                }
            }
        } catch (IOException e) {
            return false;
        }

        if (!userFound || !answerCorrect) {
            return false;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (String l : lines) {
                writer.write(l);
                writer.newLine();
            }
        } catch (IOException e) {
            return false;
        }

        return true;
    }
}