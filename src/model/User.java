package model;

public class User {

    private int id;
    private String username;
    private String password;
    private String phone;
    private String email;
    private String securityQuestion;
    private String securityAnswer;
    private String role;
    private String status;

    public User(int id, String username, String password, String phone,
                String email, String securityQuestion, String securityAnswer, String role, String status) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.phone = phone;
        this.email = email;
        this.securityQuestion = securityQuestion;
        this.securityAnswer = securityAnswer;
        this.role = role;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getSecurityQuestion() {
        return securityQuestion;
    }

    public String getSecurityAnswer() {
        return securityAnswer;
    }

    public String getRole() {
        return role;
    }
    
    public String getStatus() {
    	return status;
    }

    // Setters (optional if you need update)
    public void setPassword(String password) {
        this.password = password;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRole(String role) {
        this.role = role;
    }
    
    public void setSecurityQuestion(String securityQuestion) {
    	this.securityQuestion = securityQuestion;
    }
    
    public void setSecurityAnswer(String securityAnswer) {
    	this.securityAnswer = securityAnswer;
    }
    
    public void setStatus(String status) {
    	this.status = status;
    }

    // Optional: display info
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}