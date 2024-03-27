package no.ntnu.idi.idatt2105.quizopia.backend.model;

public class User {

    private String username;
    private String email;
    private String password;
    private Long role_id; // Foreign key reference to Roles

    // Constructors
    public User() {
    }

    public User(String username, String email, String password, Long role_id) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.role_id = role_id;
    }

    // Getters and Setters

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getRoleId() {
        return role_id;
    }

    public void setRoleId(Long role_id) {
        this.role_id = role_id;
    }

    @Override
    public String toString() {
        return "Users{" +
                ", name='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", roleId='" + role_id + '\'' +
                '}';
    }
}
